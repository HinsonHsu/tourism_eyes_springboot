package cb.tourism.domain.repository;

import cb.tourism.domain.TourRoute;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "tourroute", path="route")
public interface TourRouteRepository extends MongoRepository<TourRoute, String> {
    TourRoute findByScenicIdAndDayIndex(@Param("scenicId") String  scenicId);
    TourRoute[] findAllByScenicId(@Param("scenicId") String  scenicId);
}
