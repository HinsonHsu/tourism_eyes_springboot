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
import java.util.Random;

@Component
public class RecommendationService {

    @Autowired
    private ScenicSpotService scenicSpotService;

    @Autowired
    private TourRouteRepository tourRouteRepository;


    public JSONObject recommendStrategyBySpotId(String spotId){

//        spotId = "5afa3a9629a8e651d0269718";
//        spotId = "5b07fe8c7e72823ed8a58a7a";
        System.out.println("recommendStrategyBySpotId: "+spotId);
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
        //随机产生推荐景点， （景点较少)
        Random rand = new Random();

        System.out.println("RecommendationSpotIdListBySpotId: "+spotId);
        JSONArray array = new JSONArray();
        List<String> l = new ArrayList<>();
        l.add(RecognitionService.spots[rand.nextInt(5)]);
        l.add(RecognitionService.spots[rand.nextInt(5)]);
        l.add(RecognitionService.spots[rand.nextInt(5)]);
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

        spotId = "5b07fe8c7e72823ed8a58a7a";

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


        Random rand = new Random();

        for(int i=0; i<3; i++){
            JSONObject tmp = new JSONObject();
            ScenicSpot scenicSpot = scenicSpotService.findScenicSpotById(RecognitionService.spots[rand.nextInt(5)]);
            tmp.put("name", scenicSpot.name);
            tmp.put("score", scenicSpot.score);
            tmp.put("image_url", scenicSpot.imageUrl);
            array.add(tmp);
        }
        return array;
    }
}
