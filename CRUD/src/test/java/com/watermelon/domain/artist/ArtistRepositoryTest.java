package com.watermelon.domain.artist;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.album.AlbumRepository;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.domain.artist_album.ArtistAlbumRepository;
import com.watermelon.dto.artist.ArtistUpdateRequestDto;
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

import javax.xml.ws.Response;

import java.util.ArrayList;
import java.util.List;

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

//    @After
//    public void cleanup() {
//        artistRepository.deleteAll();
//    }

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
        artistRepository.save(Artist.builder()
                .name("member")
                .group(artist)
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
        String url = "http://localhost:" + port + "/v1/artists/" + id;

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
//        artistAlbumRepository.deleteAll();
//        albumRepository.deleteAll();

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

    @Test
    public void artist_list_by_keyword() {

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

        String keyword = "t1";
        String url = "http://localhost:" + port + "/v1/artists?keyword=" + keyword;

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
        artistAlbumRepository.deleteAll();
        albumRepository.deleteAll();
    }

    @Test
    public void artist_update() {

        // given
        Artist group1 = artistRepository.save(Artist.builder()
                .name("group1")
                .build()
        );
        Artist artist = artistRepository.save(Artist.builder()
                .name("artist")
                .group(group1)
                .build()
        );
        Artist group2 = artistRepository.save(Artist.builder()
                .name("group2")
                .build()
        );
        Album album1 = albumRepository.save(Album.builder()
                .title("title1")
                .build()
        );
        artistAlbumRepository.save(ArtistAlbum.builder()
                .artist(artist)
                .album(album1)
                .build()
        );
        Album album2 = albumRepository.save(Album.builder()
                .title("title2")
                .build()
        );

        Long id = artist.getId();
        List<Long> album_ids = new ArrayList<>();
        album_ids.add(album2.getId());

        String expectedName = "updated name";

        ArtistUpdateRequestDto requestDto = ArtistUpdateRequestDto.builder()
                .name("updated name")
                .album_id_list(album_ids)
                .artist_id(group2.getId())
                .build();

        HttpEntity<ArtistUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        String url = "http://localhost:" + port + "/v1/artists/" + id;
        System.out.println(artist.toString());

        // when
        ResponseEntity<Object> responseEntity = restTemplate
                .exchange(url, HttpMethod.PATCH, requestEntity, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Artist updatedArtist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));
        assertThat(updatedArtist.getName()).isEqualTo(expectedName);
        System.out.println(responseEntity.getBody());
        artistAlbumRepository.deleteAll();
        albumRepository.deleteAll();
    }

    @Test
    public void artist_delete() {

        // given
        Artist artist = artistRepository.save(Artist.builder()
                .name("artist")
                .build()
        );
        Long id = artist.getId();
        String url = "http://localhost:" + port + "/v1/artists/" + id;
        // when
        restTemplate.delete(url);

        // then
        Artist testArtist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아티스트가 존재하지 않습니다."));
        assertThat(testArtist.getDeletedAt()).isNotNull();
        System.out.println(testArtist.getName());
    }
}
