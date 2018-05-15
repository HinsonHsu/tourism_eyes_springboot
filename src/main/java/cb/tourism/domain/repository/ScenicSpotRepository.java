package cb.tourism.domain.repository;

import cb.tourism.domain.ScenicSpot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "scenicspot", path="spot")
public interface ScenicSpotRepository extends MongoRepository<ScenicSpot, String>{
    ScenicSpot findByName(@Param("name") String  name);
}
