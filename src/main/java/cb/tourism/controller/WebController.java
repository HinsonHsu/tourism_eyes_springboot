package cb.tourism.controller;

import cb.tourism.domain.ResponseBean;
import cb.tourism.domain.User;
import cb.tourism.domain.repository.UserRepository;
import cb.tourism.exception.UnauthorizedException;
import cb.tourism.redis.RedisService;
import cb.tourism.service.UserService;
import cb.tourism.service.WXService;
import cb.tourism.util.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    private static final Logger LOGGER = LogManager.getLogger(WebController.class);

    private UserService userService;
    @Autowired
    private WXService wxService;

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }

    /*
    Post 请求login
    @Param code 微信小程序获取的code，只能使用一次
     */
    @PostMapping("/login")
    public ResponseBean login(@RequestParam("code") String code) {
        System.out.println("code:"+code);
        JSONObject resObject = wxService.getOpenIdAndSenssion_keyByCode(code);
        String openid = resObject.getString("openid");
        if (openid == null){
            return new ResponseBean(400, "invalid code");
        }
        User user = userRepository.findByOpenId(openid);
        if (user == null){
            user = new User();
            user.setOpenId(openid);
            userRepository.save(user);
        }
        if(redisService.get(openid) == null){
            redisService.set(openid, JWTUtil.sign(openid, openid),30);
        }
        Object token = redisService.get(openid);
        redisService.set(openid, token,30);
        return new ResponseBean(200, "login success", token);

    }
    /*
    测试token是否可用，根据token来认证用户是否登录
     */
    @GetMapping("/article")
    public ResponseBean article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResponseBean(200, "You are already logged in", null);
        } else {
            return new ResponseBean(200, "You are guest", null);
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public ResponseBean requireAuth() {
        return new ResponseBean(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public ResponseBean requireRole() {
        return new ResponseBean(200, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ResponseBean requirePermission() {
        return new ResponseBean(200, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized() {
        return new ResponseBean(401, "Unauthorized", null);
    }
}
