package com.watermelon.domain.artist;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.album.AlbumRepository;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.domain.artist_album.ArtistAlbumRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArtistRepositoryTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistAlbumRepository artistAlbumRepository;

    @After
    public void cleanup() {
        artistRepository.deleteAll();
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // patch, delete Http 메서드를 사용할 수 있게하는 코드
    @Before
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    public void artist_read() {

        // given
        // 아티스트와 앨범 객체를 생성하고 매핑합니다
        Artist artist = artistRepository.save(Artist.builder()
                .name("artist")
                .build()
        );
        Album album = albumRepository.save(Album.builder()
                .title("title")
                .build()
        );
        artistAlbumRepository.save(ArtistAlbum.builder()
                .artist(artist)
                .album(album)
                .build()
        );

        Long id = artist.getId();
//        Long id = 1L;
        String url = "http://localhost:" + port + "/v1/artists/" + id;

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
        artistAlbumRepository.deleteAll();
        albumRepository.deleteAll();

    }

    @Test
    public void artist_list() {

        // given
        Artist artist1 = artistRepository.save(Artist.builder()
                .name("artist1")
                .build()
        );
        Artist artist2 = artistRepository.save(Artist.builder()
                .name("artist2")
                .build()
        );
        Album album1 = albumRepository.save(Album.builder()
                .title("album1")
                .build()
        );
        Album album2 = albumRepository.save(Album.builder()
                .title("album2")
                .build()
        );
        artistAlbumRepository.save(ArtistAlbum.builder()
                .artist(artist1)
                .album(album1)
                .build()
        );
        artistAlbumRepository.save(ArtistAlbum.builder()
                .artist(artist2)
                .album(album2)
                .build()
        );

        String url = "http://localhost:" + port + "/v1/artists";

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
        artistAlbumRepository.deleteAll();
        albumRepository.deleteAll();
    }
}
