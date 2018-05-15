package cb.tourism.controller;

import cb.tourism.domain.ResponseBean;
import cb.tourism.domain.ScenicSpot;
import cb.tourism.domain.TourRoute;
import cb.tourism.domain.User;
import cb.tourism.domain.repository.ScenicSpotRepository;
import cb.tourism.domain.repository.TourRouteRepository;
import cb.tourism.domain.repository.UserRepository;
import cb.tourism.mq.Sender;
import cb.tourism.redis.RedisService;
import cb.tourism.service.ScenicSpotService;
import cb.tourism.service.WXService;
import cb.tourism.util.AESDecode;
import cb.tourism.util.CommonUtil;
import cb.tourism.util.QCloudUpload;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
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
    @Autowired
    private WXService wxService;

    @Autowired
    private ScenicSpotRepository scenicSpotRepository;
    @Autowired
    private TourRouteRepository tourRouteRepository;
    @Autowired
    private ScenicSpotService scenicSpotService;



    @RequestMapping(value = "/imageupload", method = RequestMethod.POST)
    public ResponseBean upload(@RequestParam("photo") MultipartFile file){
        if (file.isEmpty()){
            return new ResponseBean(400, "文件为空", "请选择上传文件");
        }
        String fileName = file.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        String sufffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + sufffixName);
        String filePath = "/data/upload/image/";
        //判断当前是否为windows环境
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){
            filePath = "E://upload_image//"; //windows下路径
        }

        String newFileName = Long.toString(System.currentTimeMillis()) + sufffixName;
        File dest = new File(filePath + newFileName);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);

            //上传至腾讯云对象存储
            String destPath = "/spot_image/";
            QCloudUpload.SimpleUploadFileFromLocal(filePath, newFileName, destPath);

            return new ResponseBean(200, "upload success", destPath+newFileName);
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return new ResponseBean(500, "上传失败", "请稍后重试");
    }

    @RequestMapping("/tourroutetest")
    public String tourroteTest(){
        TourRoute tourRoute = new TourRoute();
        tourRoute.setScenicId("5afa3a9629a8e651d0269718");
        tourRoute.setDayRoute("中国花园与中国城 -海德公园 -圣玛丽大教堂 -马丁广场 -悉尼塔 -Nando’s -喜来登公园酒店");
        tourRoute.setDayIndex(3);
        tourRouteRepository.save(tourRoute);
//        System.out.println(tourRoute.id);
//        String spotId = "5afa3a9629a8e651d0269718";
//        ScenicSpot scenicSpot =  scenicSpotService.findScenicSpotById(spotId);
//        System.out.println("he");
        return "OKay";
    }

    @RequestMapping("/scenicspottest")
    public String scenicspotTest(){
        ScenicSpot ss = new ScenicSpot();
        ss.setName("澳大利亚");
        ss.setTitle("澳洲：带你探寻不一样的澳洲之旅");
        ss.setDes("澳大利亚是全球土地面积最大的国家，国土面积比整个西欧大一半，澳大利亚不仅国土辽阔，而且物产丰富。其领土面积7692024平方公里，四面环海，是世界上唯一国土覆盖一整个大陆的国家，因此也称“澳洲”。拥有很多独特的动植物和自然景观的澳大利亚，是一个奉行多元文化的移民国家。");
        ss.setScore(4.5f);
        ss.setTime("每年十月到次年二月");
        ss.setRecommendTakeDay(8);
        ss.setVisaDegree("一般");
        System.out.println("login: " + redisService.get("23423423425324"));
        scenicSpotRepository.save(ss);
        return "OKay";
    }

    @RequestMapping("/rabbit")
    public String rabbitTest(){
        sender.send();
        redisService.set("login", "today");
        System.out.println("login: " + redisService.get("23423423425324"));
        return "OKay";
    }

    @RequestMapping(value = "/code", method = RequestMethod.POST)
    public String codeTest(@RequestParam(value="code") String code){

        return wxService.getOpenIdAndSenssion_keyByCode(code).toJSONString();
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
