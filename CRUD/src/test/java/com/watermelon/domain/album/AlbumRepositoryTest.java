package com.watermelon.domain.album;

import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.artist.ArtistRepository;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.domain.artist_album.ArtistAlbumRepository;
import com.watermelon.domain.music.Music;
import com.watermelon.domain.music.MusicRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistAlbumRepository artistAlbumRepository;

    @Autowired
    private MusicRepository musicRepository;

    @After
    public void cleanup() {
        artistAlbumRepository.deleteAll();
        musicRepository.deleteAll();
        artistRepository.deleteAll();
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
        // 아티스트와 앨범 객체를 생성하고 매핑합니다
        Album album = albumRepository.save(Album.builder()
                .title("title")
                .build()
        );
        Artist artist = artistRepository.save(Artist.builder()
                .name("artist")
                .build()
        );
        musicRepository.save(Music.builder()
                .title("music1")
                .composer("composer")
                .songwriter("writer")
                .album(album)
                .build()
        );
        musicRepository.save(Music.builder()
                .title("music2")
                .composer("composer")
                .songwriter("writer")
                .album(album)
                .build()
        );
        artistAlbumRepository.save(ArtistAlbum.builder()
                .album(album)
                .artist(artist)
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
        Album album1 = albumRepository.save(Album.builder()
                .title("album1")
                .build()
        );
        Album album2 = albumRepository.save(Album.builder()
                .title("album2")
                .build()
        );
        Artist artist1 = artistRepository.save(Artist.builder()
                .name("artist1")
                .build()
        );
        Artist artist2 = artistRepository.save(Artist.builder()
                .name("artist2")
                .build()
        );
        musicRepository.save(Music.builder()
                .title("music1")
                .composer("composer")
                .songwriter("writer")
                .album(album1)
                .build()
        );
        musicRepository.save(Music.builder()
                .title("music3")
                .composer("composer")
                .songwriter("writer")
                .album(album1)
                .build()
        );
        musicRepository.save(Music.builder()
                .title("music2")
                .composer("composer")
                .songwriter("writer")
                .album(album2)
                .build()
        );
        artistAlbumRepository.save(ArtistAlbum.builder()
                .album(album1)
                .artist(artist1)
                .build()
        );
        artistAlbumRepository.save(ArtistAlbum.builder()
                .album(album2)
                .artist(artist2)
                .build()
        );

        String url = "http://localhost:" + port + "/v1/albums";

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
        Artist artist1 = artistRepository.save(Artist.builder()
                .name("artist1")
                .build()
        );
        artistAlbumRepository.save(ArtistAlbum.builder()
                .album(album)
                .artist(artist1)
                .build()
        );
        Artist artist2 = artistRepository.save(Artist.builder()
                .name("artist2")
                .build()
        );
        musicRepository.save(Music.builder()
                .title("music1")
                .composer("composer")
                .songwriter("writer")
                .album(album)
                .build()
        );
        Music music = musicRepository.save(Music.builder()
                .title("music2")
                .composer("composer")
                .songwriter("writer")
                .build()
        );

        Long updatedId = album.getId();
        String expectedTitle = "title update";

        List<Long> artist_ids = new ArrayList<>();
        artist_ids.add(artist2.getId());

        List<Long> musicIdList = new ArrayList<>();
        musicIdList.add(music.getId());

        AlbumUpdateRequestDto requestDto = AlbumUpdateRequestDto.builder()
                .title(expectedTitle)
                .artistIdList(artist_ids)
                .musicIdList(musicIdList)
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
        assertThat(testAlbum.getDeletedAt()).isNotNull();
        System.out.println(testAlbum.getTitle());
    }
}
