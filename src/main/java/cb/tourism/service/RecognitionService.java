package cb.tourism.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class RecognitionService {
    public JSONObject parseFromString(String str){
        JSONObject jsonObject = JSONObject.parseObject(str);
        JSONArray res = jsonObject.getJSONArray("tags");
        JSONObject res0 = res.getJSONObject(0);
        res0.put("description", "佛香阁是北京市颐和园的主体建筑，建筑在万寿山前山高20米的方形台基上，南对昆明湖，背靠智慧海，以它为中心的各建筑群严整而对称地向两翼展开，形成众星捧月之势，气派相当宏伟。佛香阁高41米，8面3层4重檐，阁内有8根巨大铁梨木擎天柱，结构相当复杂，为古典建筑精品。");
        res0.put("spotId", "5afa131529a8e61ff0a6ae29");
        return res0;
    }
}
