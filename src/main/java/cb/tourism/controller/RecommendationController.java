package cb.tourism.controller;

import cb.tourism.domain.ResponseBean;
import cb.tourism.service.RecommendationService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RecommendationController {
    @Autowired
    private RecommendationService recommendationService;
    @RequestMapping(value = "/strategy", method = RequestMethod.GET)
    public ResponseBean recommendationSpot(@RequestParam("spotId") String spotId) {
        JSONObject res = new JSONObject();
        JSONArray arraySpots = recommendationService.RecommendationSpotIdListBySpotId(spotId);
        res.put("place", recommendationService.recommendStrategyBySpotId(spotId));
        res.put("routes", recommendationService.routeBySpotId(spotId));
        res.put("recommend", recommendationService.recommendSpot(spotId));
        return new ResponseBean(200, "success", res);
    }

    @RequestMapping(value = "/analogy", method = RequestMethod.GET)
    public ResponseBean recommendationRoute(@RequestParam("spotId") String spotId) {
        JSONObject object = new JSONObject();
        JSONArray array = recommendationService.RecommendationSpotIdListBySpotId(spotId);
        if(array.isEmpty()){
            return new ResponseBean(200, "fail", "无相关景点！");
        }else{
            object.put("places", array);
            return new ResponseBean(200, "success", object);
        }

    }

}
