package cb.tourism.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import cb.tourism.domain.User;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "user", path="user")
public interface UserRepository extends MongoRepository<User, String> {
    User findByOpenId(@Param("openId") String  openId);
    User findByUserName(@Param("userName") String  userName);
}