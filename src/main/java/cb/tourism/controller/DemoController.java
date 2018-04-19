package cb.tourism.controller;

import cb.tourism.domain.User;
import cb.tourism.domain.repository.UserRepository;
import cb.tourism.mq.Sender;
import cb.tourism.redis.RedisService;
import cb.tourism.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoController {

    @Autowired
    private Sender sender;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/rabbit")
    public String rabbitTest(){
        sender.send();
        redisService.set("login", "today");
        System.out.println("login: " + redisService.get("23423423425324"));
        return "OKay";
    }

    @RequestMapping(value = "/code", method = RequestMethod.POST)
    public String codeTest(@RequestParam(value="code") String code){
        System.out.println("code: " + code);
        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        try {
            String requestUrl = WX_URL.replace("APPID", "wx32672e87f03da727").
                    replace("SECRET", "601debbb04689794fcd84080e4098bf2").replace("JSCODE", code).
                    replace("authorization_code", "authorization_code");
            // 发起GET请求获取凭证
            JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
            System.out.println(jsonObject.toJSONString());
        } catch (Exception e) {
            System.out.println(e);
        }
        return "OKay";
    }

    @RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
    public String ajaxLogin(@RequestParam(value="openId") String openId, @RequestParam(value="userName") String userName) {
        User user = userRepository.findByOpenId("userName");
        if (user == null){
            System.out.println("用户不存在！");
            user = new User();
            user.setOpenId(openId);
            user.setUserName(userName);
            userRepository.save(user);
        }
        JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(openId, userName);
        try {
            subject.login(token);
            jsonObject.put("token", subject.getSession().getId());
            jsonObject.put("msg", "登录成功");
        } catch (IncorrectCredentialsException e) {
            jsonObject.put("msg", "密码错误");
        } catch (LockedAccountException e) {
            jsonObject.put("msg", "登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
            jsonObject.put("msg", "该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
