package cb.tourism.controller;

import cb.tourism.domain.User;
import cb.tourism.domain.repository.CustomerRepository;
import cb.tourism.mq.Sender;
import cb.tourism.redis.RedisService;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;

@RestController
public class DemoController {

    @Autowired
    private Sender sender;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CustomerRepository cp;


    @RequestMapping("/rabbit")
    public String rabbitTest(){
        sender.send();
        redisService.set("login", "today");
        System.out.println("login: " + redisService.get("login"));
        return "OKay";
    }

    @RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
    public String ajaxLogin(@RequestParam(value="userName") String userName, @RequestParam(value="password") String password) {
        User user = new User();
        user.setFirstName(userName);
        user.setLastName(password);
        cp.save(user);
        JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
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
