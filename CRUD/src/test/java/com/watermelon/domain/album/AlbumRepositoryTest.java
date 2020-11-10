package com.watermelon.domain.album;

import com.watermelon.dto.album.AlbumUpdateRequestDto;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;

    @After
    public void cleanup() {
        albumRepository.deleteAll();
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
    public void album_read() {
        // given
        Album album = albumRepository.save(Album.builder()
                .title("title")
                .information("first album")
                .build()
        );

        Long id = album.getId();
        String url = "http://localhost:" + port + "/v1/albums/" + id;

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void album_list() {

        // given
        albumRepository.save(Album.builder()
                .title("title1")
                .information("first album")
                .build()
        );
        albumRepository.save(Album.builder()
                .title("title2")
                .information("second album")
                .build()
        );

        String url = "http://localhost:" + port + "/v1/albums/";

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void album_update() {

        // given
        Album album = albumRepository.save(Album.builder()
                .title("title")
                .information("first album")
                .build()
        );

        Long updatedId = album.getId();
        String expectedTitle = "title update";

        AlbumUpdateRequestDto requestDto = AlbumUpdateRequestDto.builder()
                .title(expectedTitle)
                .build();

        HttpEntity<AlbumUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        String url = "http://localhost:" + port + "/v1/albums/" + updatedId;

        // when
        ResponseEntity<Object> responseEntity = restTemplate
                .exchange(url, HttpMethod.PATCH, requestEntity, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());

        Album savedAlbum = albumRepository.findById(updatedId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 앨범"));
        assertThat(savedAlbum.getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    public void album_delete() {

        // given
        // 테스트 유저 생성
        Album album = albumRepository.save(Album.builder()
                .title("title")
                .information("information")
                .build()
        );

        // 테스트 유저의 id
        Long deletedId = album.getId();
        String url = "http://localhost:" + port + "/v1/albums/" + deletedId;

        // when
        restTemplate.delete(url);

        // then
        Album testAlbum = albumRepository.findById(deletedId)
                .orElseThrow(() -> new IllegalArgumentException("해당 앨범이 존재하지 않습니다."));
        assertThat(testAlbum.getDeleted_at()).isNotNull();
        System.out.println(testAlbum.getTitle());
    }
}
