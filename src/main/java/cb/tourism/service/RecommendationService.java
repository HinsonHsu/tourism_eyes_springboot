package cb.tourism.service;

import cb.tourism.domain.ScenicSpot;
import cb.tourism.domain.TourRoute;
import cb.tourism.domain.repository.TourRouteRepository;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecommendationService {

    @Autowired
    private ScenicSpotService scenicSpotService;

    @Autowired
    private TourRouteRepository tourRouteRepository;


    public JSONObject recommendStrategyBySpotId(String spotId){

        spotId = "5afa3a9629a8e651d0269718";

        JSONObject object = new JSONObject();
        ScenicSpot scenicSpot = scenicSpotService.findScenicSpotById(spotId);
        object.put("spotId", scenicSpot.id);
        object.put("name", scenicSpot.name);
        object.put("des", scenicSpot.des);
        object.put("scores", scenicSpot.score);
        object.put("day", scenicSpot.recommendTakeDay);
        object.put("visa", scenicSpot.visaDegree);
        object.put("image_url", scenicSpot.imageUrl);
        object.put("time", scenicSpot.time);
        return  object;
    }

    public JSONArray RecommendationSpotIdListBySpotId(String spotId) {
        JSONArray array = new JSONArray();
        List<String> l = new ArrayList<>();
        l.add("5afa3a9629a8e651d0269718");
        l.add("5afa3a9629a8e651d0269718");
        l.add("5afa3a9629a8e651d0269718");
        for(int i=0; i < l.size(); i++){
            ScenicSpot scenicSpot = scenicSpotService.findScenicSpotById(l.get(i));
            JSONObject tmp = new JSONObject();
            tmp.put("spotId", scenicSpot.id);
            tmp.put("title", scenicSpot.title);
            tmp.put("des", scenicSpot.des);
            tmp.put("time", scenicSpot.time);
            tmp.put("scores", scenicSpot.score);
            tmp.put("image_url", scenicSpot.imageUrl);
            array.add(tmp);
        }
        return array;
    }

    public JSONArray routeBySpotId(String spotId){

        spotId = "5afa3a9629a8e651d0269718";

        TourRoute[] tourRoutes = tourRouteRepository.findAllByScenicId(spotId);
        JSONArray array = new JSONArray();
        for(TourRoute tr: tourRoutes){
            JSONObject tmp = new JSONObject();
            tmp.put("day", tr.dayIndex);
            tmp.put("route", tr.dayRoute);
            tmp.put("image_url", "https://calabash-brothers-eyes-1256400655.cos.ap-beijing.myqcloud.com/spot_image/1526370027095.jpg");
            array.add(tmp);
        }
        return array;
    }

    public JSONArray recommendSpot(String spotId){
        spotId = "5afa3a9629a8e651d0269718";
        JSONArray array = new JSONArray();
        for(int i=0; i<3; i++){
            JSONObject tmp = new JSONObject();
            tmp.put("name", "墨尔本" + i);
            tmp.put("score", 4.6);
            tmp.put("image_url", "https://calabash-brothers-eyes-1256400655.cos.ap-beijing.myqcloud.com/spot_image/1526370027095.jpg");
            array.add(tmp);
        }
        return array;
    }
}
