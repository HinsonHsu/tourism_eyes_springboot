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
//        TourRoute tourRoute = new TourRoute();
//        tourRoute.setScenicId("5afa3a9629a8e651d0269718");
//        tourRoute.setDayRoute("中国花园与中国城 -海德公园 -圣玛丽大教堂 -马丁广场 -悉尼塔 -Nando’s -喜来登公园酒店");
//        tourRoute.setDayIndex(3);
//        tourRouteRepository.save(tourRoute);

        for(int i= 0; i<3; i++){
            TourRoute tourRoute = new TourRoute();
            tourRoute.setScenicId("5b07fe8c7e72823ed8a58a7a");
            tourRoute.setDayRoute("昆明湖 -谐趣园 -万寿山 -苏州街 -听鹂馆 -十七孔桥");
            tourRoute.setDayIndex(i+1);
            tourRouteRepository.save(tourRoute);
        }
        return "OKay";
    }

    @RequestMapping("/scenicspottest")
    public String scenicspotTest(){
//        ScenicSpot ss = new ScenicSpot();
//        ss.setName("澳大利亚");
//        ss.setTitle("澳洲：带你探寻不一样的澳洲之旅");
//        ss.setDes("澳大利亚是全球土地面积最大的国家，国土面积比整个西欧大一半，澳大利亚不仅国土辽阔，而且物产丰富。其领土面积7692024平方公里，四面环海，是世界上唯一国土覆盖一整个大陆的国家，因此也称“澳洲”。拥有很多独特的动植物和自然景观的澳大利亚，是一个奉行多元文化的移民国家。");
//        ss.setScore(4.5f);
//        ss.setTime("每年十月到次年二月");
//        ss.setRecommendTakeDay(8);
//        ss.setVisaDegree("一般");

//        ss.setName("万寿山");
//        ss.setTitle("颐和园：走进万寿山");
//        ss.setDes("万寿山位于颐和园内，前临昆明湖。明弘治七年（1494）孝宗的乳母助圣夫人罗氏在山前建园静寺，清初，曾作宫廷养马的草料场。 乾隆十五年（1750）为庆祝皇太后六十寿辰于园静寺旧址建大报恩延寿寺。次年将山改名为万寿山。并将开拓昆明湖的土方按照原布局的需要堆放在山上，使东西两坡舒缓而对称，成为全园的主体。 现存的是英法联军烧毁后慈禧重新建造的。从山脚的“云辉玉宇”牌楼，经排云门，二宫门，排云殿，德辉殿，佛香阁，直至山顶的智慧海，形成一条层层上升的中轴线。");
//        ss.setScore(4.5f);
//        ss.setTime("旺季4月1日-10月31日");
//        ss.setRecommendTakeDay(8);
//        ss.setVisaDegree("无");
//        ss.setImageUrl("https://calabash-brothers-eyes-1256400655.cos.ap-beijing.myqcloud.com/image/1527250453769.jpg");
//        scenicSpotRepository.save(ss);

//
//        ss = new ScenicSpot();
//        ss.setName("东宫门");
//        ss.setTitle("东宫门");
//        ss.setDes("东宫门是颐和园的正门。宫门为五扇，三明两暗。正中设三个门洞，中门叫御路门，为慈禧太后和皇帝、皇后进出专用的；两旁门洞供王公大臣出入。门檐下是光绪皇帝御笔题写的“颐和园”匾额。");
//        ss.setScore(4.3f);
//        ss.setTime("旺季4月1日-10月31日");
//        ss.setRecommendTakeDay(3);
//        ss.setVisaDegree("无");
//        ss.setImageUrl("https://calabash-brothers-eyes-1256400655.cos.ap-beijing.myqcloud.com/image/1527250854867.jpg");
//        scenicSpotRepository.save(ss);
//
//        ss = new ScenicSpot();
//        ss.setName("佛香阁");
//        ss.setTitle("关于佛香阁");
//        ss.setDes("佛香阁是颐和园的主体建筑，建筑在万寿山前山高21米的方形台基上，南对昆明湖，背靠智慧海，以它为中心的各建筑群严整而对称地向两翼展开，形成众星捧月之势，气派相当宏伟。佛香阁高40米，8面3层4重檐，阁内有8根巨大铁梨木擎天柱，结构相当复杂，为古典建筑精品。");
//        ss.setScore(4.3f);
//        ss.setTime("旺季（4月1日-10月31日）8:30-17:00");
//        ss.setRecommendTakeDay(3);
//        ss.setVisaDegree("一般");
//        ss.setImageUrl("https://calabash-brothers-eyes-1256400655.cos.ap-beijing.myqcloud.com/image/1527251035711.jpg");
//        scenicSpotRepository.save(ss);
//
//        ss = new ScenicSpot();
//        ss.setName("排云殿");
//        ss.setTitle("颐和园：排云殿");
//        ss.setDes("排云殿位于颐和园万寿山前建筑的中心部位，原是乾隆为其母后60寿辰而建的大报恩延寿寺，慈禧重建的时候更名为排云殿。“排云”二字取自晋代郭璞《游仙诗》：“神仙排云出，但见金银台”。这组建筑是颐和园最为壮观的建筑群。");
//        ss.setScore(4.5f);
//        ss.setTime("旺季4月1日-10月31日");
//        ss.setRecommendTakeDay(3);
//        ss.setVisaDegree("无");
//        ss.setImageUrl("https://calabash-brothers-eyes-1256400655.cos.ap-beijing.myqcloud.com/image/1527251117666.jpg");
//        scenicSpotRepository.save(ss);
//
//        ss = new ScenicSpot();
//        ss.setName("昆明湖");
//        ss.setTitle("颐和园：走进昆明湖");
//        ss.setDes("昆明湖位于北京的颐和园内，约为它总面积的四分之三。原为北京西北郊众多泉水汇聚成的天然湖泊，曾有七里泺、大泊湖等名称。 昆明湖的前身叫瓮山泊，因万寿山前身有瓮山之名而得名瓮山泊。瓮山泊因地处北京西郊，又被人们称为西湖。 元朝定都北京后，至元二十九年（公元1292年），水利学家郭守敬主持开挖通惠河，引昌平神山泉水及沿途流水及西山一带泉水汇引注入湖中，成为元大都城内接济漕运的水库。瓮山泊始成为调济京城用水的蓄水库。 明代湖中多植荷花，周围水田种植稻谷，湖旁又有寺院、亭台之胜，因为这一带风景优美，山水俱佳，酷似江南风景，时人尚有“西湖十寺”与“西湖十景”之誉。明武宗、明神宗都曾在此泛舟钓鱼取乐。明朝一些诗人常把西湖周围地区的自然风光描绘成宛如“江南风景”，“环湖十里，一郡之盛观”。明朝时，每年桃红柳绿时，京城百姓扶老携幼，争往西湖踏青赏春，名曰：“耍西湖景”。 至清朝乾隆十五年（1750），乾隆皇帝决定在瓮山一带兴建清漪园，将湖开拓，成为现在的规模，并取汉武帝在长安开凿昆明池操演水战的故事，命名昆明湖，乾隆皇帝在昆明湖泛舟的诗中写到“何处燕山最畅情，无双风月属昆明。”1860年，清漪园被英法联军焚毁。光绪十二年（1886年），慈禧太后以筹措海军经费的名义动用500多万两白银重建，改称颐和园，作消夏游乐养老地。");
//        ss.setScore(4.5f);
//        ss.setTime("旺季4月1日-10月31日");
//        ss.setRecommendTakeDay(3);
//        ss.setVisaDegree("无");
//        ss.setImageUrl("https://calabash-brothers-eyes-1256400655.cos.ap-beijing.myqcloud.com/image/1527251202689.jpg");
//        scenicSpotRepository.save(ss);

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
