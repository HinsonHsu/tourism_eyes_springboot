package cb.tourism.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import cb.tourism.domain.User;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "user", path="user")
public interface CustomerRepository extends MongoRepository<User, String> {

    List<User> findByLastName(@Param("name") String name);

}