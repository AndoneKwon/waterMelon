package auth.watermelon.domain.user;

import auth.watermelon.exception.UserNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    public void userJoin() {
        // given
        String email = "test@test.com";
        String password = "password";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);

        String url = "http://localhost:" + port + "/v1/sign-up";
        HttpEntity<Map> requestEntity = new HttpEntity<>(requestBody);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
                requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void userLogin() {

        // given
        String email = "test@test.com";
        String password = "password";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);

        String urlJoin = "http://localhost:" + port + "/v1/sign-up";
        String urlLogin = "http://localhost:" + port + "/v1/sign-in";

        HttpEntity<Map> requestEntityJoin = new HttpEntity<>(requestBody);
        HttpEntity<Map> requestEntityLogin = new HttpEntity<>(requestBody);

        // when
        restTemplate.exchange(urlJoin, HttpMethod.POST, requestEntityJoin, Long.class);
        ResponseEntity<String> responseEntity = restTemplate.exchange(urlLogin, HttpMethod.POST,
                requestEntityLogin, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void UserAuthorizationPassed() {

        // given
        String email = "test@test.com";
        String password = "password";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);

        String urlJoin = "http://localhost:" + port + "/v1/sign-up";
        String urlLogin = "http://localhost:" + port + "/v1/sign-in";
        String urlTest = "http://localhost:" + port + "/v1/test";

        HttpEntity<Map> requestEntityJoin = new HttpEntity<>(requestBody);
        HttpEntity<Map> requestEntityLogin = new HttpEntity<>(requestBody);
        HttpHeaders headers = new HttpHeaders();

        try {
            // when
            restTemplate.exchange(urlJoin, HttpMethod.POST, requestEntityJoin, Long.class);
            ResponseEntity<String> responseEntityLogin = restTemplate.exchange(urlLogin, HttpMethod.POST, requestEntityLogin, String.class);

            headers.add("X-AUTH-TOKEN", responseEntityLogin.getBody());
            headers.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<String> responseEntity = restTemplate.exchange(urlTest, HttpMethod.GET,
                    new HttpEntity<>(headers), String.class);

            // then
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            System.out.println(responseEntity.getBody());
        } catch (Exception e) {
           throw new UserNotFoundException(email);
        }
    }

    @Test
    public void UserNoAuthorization() {

        // given
        String url = "http://localhost:" + port + "/v1/test";

        // when
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        System.out.println(responseEntity.getBody());
    }
}
