package cb.tourism.service;

import cb.tourism.domain.User;
import cb.tourism.domain.UserBean;
import cb.tourism.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public UserBean getUser(String openId) {
//        List<User> users = customerRepository.findByLastName("TestPassword");
//        for(User user: users){
//            System.out.println(user.openId);
//        }
        User user1 = userRepository.findByOpenId(openId);
        if(user1 == null){
            System.out.println("用户不存在");
            user1 = new User();
            user1.setOpenId(openId);
            user1.setUserName(openId);
            userRepository.save(user1);
        } else {
            System.out.println(user1.getOpenId());
        }
        // 没有此用户直接返回null
        if (! DataSource.getData().containsKey(openId))
            return null;

        UserBean user = new UserBean();
        Map<String, String> detail = DataSource.getData().get(openId);

        user.setUsername(openId);
        user.setPassword(detail.get("password"));
        user.setRole(detail.get("role"));
        user.setPermission(detail.get("permission"));
        return user;
    }
}
