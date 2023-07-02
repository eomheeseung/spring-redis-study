package example.redis_study.repository;

import example.redis_study.LogoutAccessToken;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken, String> {
    Optional<LogoutAccessToken> findByUsername(String username);
}
