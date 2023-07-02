package example.redis_study.test;

import example.redis_study.EmbeddedRedisConfig;
import example.redis_study.LogoutAccessToken;
import example.redis_study.repository.LogoutAccessTokenRedisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataRedisTest
@Import(EmbeddedRedisConfig.class)
@ActiveProfiles("test")
public class LogoutAccessTokenRedisRepositoryTest {
    @Autowired
    LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    @BeforeEach
    void clear() {
        logoutAccessTokenRedisRepository.deleteAll();
    }

    @DisplayName("save")
    @Test
    public void save() {
        // given
        String accessToken = "accessToken";
        String username = "username";
        Long expiration = 3000L;
        LogoutAccessToken logoutAccessToken = LogoutAccessToken.createLogoutAccessToken(accessToken, username, expiration);

        // when
        logoutAccessTokenRedisRepository.save(logoutAccessToken);

        // then
        LogoutAccessToken find = logoutAccessTokenRedisRepository.findById(accessToken).get();

        assertAll(() -> assertEquals(accessToken, find.getId()),
                () -> assertEquals(username, find.getUsername()),
                () -> assertEquals(expiration / 1000, find.getExpiration()));
    }
}
