package cb.tourism.service;

import cb.tourism.domain.ScenicSpot;
import cb.tourism.util.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecognitionService {

    @Autowired
    private ScenicSpotService scenicSpotService;

    public static String [] spots =  {"5b07fe8c7e72823ed8a58a7a", "5b08011c7e72824fcc6f6515",
            "5b08011c7e72824fcc6f6516","5b08011c7e72824fcc6f6517", "5b08011c7e72824fcc6f6518"};
    public JSONObject parseFromString(String str){
        System.out.println("开始RecognitionService");
        String rec = HttpRequest.sendPost("http://127.0.0.1:5000", "image=1.jpg");
        System.out.println("识别结果：" + rec);
        JSONObject jsonObject = JSONObject.parseObject(str);
        JSONArray res = jsonObject.getJSONArray("tags");
        JSONObject res0 = res.getJSONObject(0);
        res0.put("description", "佛香阁是北京市颐和园的主体建筑，建筑在万寿山前山高20米的方形台基上，南对昆明湖，背靠智慧海，以它为中心的各建筑群严整而对称地向两翼展开，形成众星捧月之势，气派相当宏伟。佛香阁高41米，8面3层4重檐，阁内有8根巨大铁梨木擎天柱，结构相当复杂，为古典建筑精品。");
        res0.put("spotId", "5afa131529a8e61ff0a6ae29");
        return res0;
    }

    public JSONObject parseFromStringByAlgorithm(String str){
        System.out.println("开始RecognitionService");

        System.out.println(str);
        String rec = HttpRequest.sendPost("http://127.0.0.1:5000", "image=" + str);
        System.out.println("识别结果：" + rec);

        JSONObject jsonObject = JSONObject.parseObject(rec);
        int index = (int)jsonObject.get("index");
        ScenicSpot scenicSpot = scenicSpotService.findScenicSpotById(spots[index]);

        JSONObject res0 = new JSONObject();
        res0.put("spotId", scenicSpot.id);
        res0.put("value", scenicSpot.name);
        res0.put("description", scenicSpot.des);
        return res0;
    }
}
