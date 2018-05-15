package cb.tourism.service;

import cb.tourism.domain.ScenicSpot;
import cb.tourism.domain.repository.ScenicSpotRepository;
import cb.tourism.domain.repository.TourRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ScenicSpotService{
    @Autowired
    private ScenicSpotRepository scenicSpotRepository;
    @Autowired
    private TourRouteRepository tourRouteRepository;

    public ScenicSpot findScenicSpotById(String spotId){
        ScenicSpot scenicSpot = scenicSpotRepository.findById(spotId).get();
        System.out.println("sceicSpot:" + scenicSpot.id);
        return scenicSpot;
    }
}
