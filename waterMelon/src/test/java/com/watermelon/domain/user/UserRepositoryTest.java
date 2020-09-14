package com.watermelon.domain.user;

import com.watermelon.dto.user.UserUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

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
    public void 회원가입_조회() {
        // given
        String email = "test@test.com";
        String nickname = "nickname";
        String password = "password";
        String profile = "profile";
        String salt = "salt";
        Byte role = 1;

        userRepository.save(User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .profile(profile)
                .salt(salt)
                .role(role)
                .build());

        // when
        List<User> userList = userRepository.findAll();

        // then
        User user = userList.get(1);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getProfile()).isEqualTo(profile);
        assertThat(user.getSalt()).isEqualTo(salt);
        assertThat(user.getRole()).isEqualTo(role);
    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2020, 8, 25, 0, 0, 0);
        userRepository.save(User.builder()
                .email("email")
                .nickname("nickname")
                .password("password")
                .build()
        );
        // when
        List<User> userList = userRepository.findAll();

        // then
        User user = userList.get(0);

        System.out.println(">>>>>>>> created_at=" + user.getCreated_at() +
                ", updated_at=" + user.getUpdated_at());

        assertThat(user.getCreated_at()).isAfter(now);
        assertThat(user.getUpdated_at()).isAfter(now);
    }

    @Test
    public void user_리스트() throws Exception {
        // given
        int id = 1;
        String url = "http://localhost:" + port + "/v1/users";

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        
        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assertThat(responseEntity.getBody()).isGreaterThan(0L);
        System.out.println(responseEntity.getBody());
    }

    @Test
    // 사용자 프로필 출력 테스트
    public void user_profile() throws Exception {
        // given
        Long id = 1L;
        String url = "http://localhost:" + port + "/v1/profile/?id=" + id;

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);
        System.out.println(responseEntity.getBody());
    }

    @Test
    // 사용자 수정 테스트
    public void user_update() throws Exception {
        // given
        // 테스트 유저 생성
        User savedUser = userRepository.save(User.builder()
                .email("email")
                .nickname("nickname")
                .password("password")
                .profile("profile")
                .build()
        );

        // 테스트 유저의 id와 수정할 텍스트
        Long updateId = savedUser.getId();
        String expectedEmail = "email2";

        // 변경 요청 폼 생성
        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder()
                .email(expectedEmail)
                .build();

        String url = "http://localhost:" + port + "/v1/profile/" + updateId;

        HttpEntity<UserUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.PATCH,
                requestEntity, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);
        System.out.println(responseEntity.getBody());

        User user = userRepository.findById(updateId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다"));
        assertThat(user.getEmail()).isEqualTo(expectedEmail);
    }

    @Test
    // 사용자 삭제 테스트
    public void user_delete() throws Exception {
        // given
        // 테스트 유저 생성
        User savedUser = userRepository.save(User.builder()
                .email("email")
                .nickname("nickname")
                .password("password")
                .profile("profile")
                .build()
        );

        // 테스트 유저의 id
        Long deleteId = savedUser.getId();

        String url = "http://localhost:" + port + "/v1/profile/" + deleteId;

        // when
        restTemplate.delete(url);

        // then
        User user = userRepository.findById(deleteId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다"));
        assertThat(user.getDeleted_at()).isNotNull();
        System.out.println(user.getDeleted_at());
    }
}
