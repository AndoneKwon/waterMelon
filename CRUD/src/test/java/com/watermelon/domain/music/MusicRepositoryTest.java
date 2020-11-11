package com.watermelon.domain.music;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.album.AlbumRepository;
import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.artist.ArtistRepository;
import com.watermelon.domain.artist_album.ArtistAlbumRepository;
import com.watermelon.domain.artist_music.ArtistMusic;
import com.watermelon.domain.artist_music.ArtistMusicRepository;
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
public class MusicRepositoryTest {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistMusicRepository artistMusicRepository;

    @After
    public void cleanup() {
        artistMusicRepository.deleteAll();
        musicRepository.deleteAll();
        albumRepository.deleteAll();
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
    public void music_read() {

        // given
        Album album = albumRepository.save(Album.builder()
                .title("album1")
                .build()
        );
        Music music = musicRepository.save(Music.builder()
                .title("music1")
                .composer("composer1")
                .songwriter("writer1")
                .album(album)
                .build()
        );
        Artist artist = artistRepository.save(Artist.builder()
                .name("artist1")
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .music(music)
                .artist(artist)
                .build()
        );

        Long id = music.getId();
        String url = "http://localhost:" + port + "/v1/musics/" + id;

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void music_list() {

        // given
        Album album = albumRepository.save(Album.builder()
                .title("album1")
                .build()
        );
        Music music1 = musicRepository.save(Music.builder()
                .title("music1")
                .composer("composer")
                .songwriter("writer")
                .album(album)
                .build()
        );
        Music music2 = musicRepository.save(Music.builder()
                .title("music2")
                .composer("composer")
                .songwriter("writer")
                .album(album)
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
        artistMusicRepository.save(ArtistMusic.builder()
                .music(music1)
                .artist(artist1)
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .music(music1)
                .artist(artist2)
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .music(music2)
                .artist(artist1)
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .music(music2)
                .artist(artist2)
                .build()
        );

        String url = "http://localhost:" + port + "/v1/musics";

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }
}
