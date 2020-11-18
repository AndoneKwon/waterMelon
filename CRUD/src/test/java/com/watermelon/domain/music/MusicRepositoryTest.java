package com.watermelon.domain.music;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.album.AlbumRepository;
import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.artist.ArtistRepository;
import com.watermelon.domain.artist_album.ArtistAlbumRepository;
import com.watermelon.domain.artist_music.ArtistMusic;
import com.watermelon.domain.artist_music.ArtistMusicRepository;
import com.watermelon.dto.music.MusicUpdateRelationRequestDto;
import com.watermelon.dto.music.MusicUpdateRequestDto;
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
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

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

    @Test
    public void music_list_by_keyword() {

        // given
        Album album = albumRepository.save(Album.builder()
                .title("album1")
                .build()
        );
        Music music1 = musicRepository.save(Music.builder()
                .title("first music")
                .composer("composer")
                .songwriter("writer")
                .album(album)
                .build()
        );
        Music music2 = musicRepository.save(Music.builder()
                .title("second music")
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

        String keyword = "first";
        String url = "http://localhost:" + port + "/v1/musics?keyword=" + keyword;

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void music_update() {

        // given
        Album album1 = albumRepository.save(Album.builder()
                .title("이전 앨범")
                .build()
        );
        Album album2 = albumRepository.save(Album.builder()
                .title("다른 앨범")
                .build()
        );
        Music music = musicRepository.save(Music.builder()
                .title("이전 제목")
                .composer("이전 composer")
                .songwriter("이전 writer")
                .album(album1)
                .build()
        );
        Artist artist1 = artistRepository.save(Artist.builder()
                .name("아티스트1")
                .build()
        );
        Artist artist2 = artistRepository.save(Artist.builder()
                .name("아티스트2")
                .build()
        );
        Artist artist3 = artistRepository.save(Artist.builder()
                .name("아티스트3")
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .music(music)
                .artist(artist1)
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .music(music)
                .artist(artist2)
                .build()
        );

        Long id = music.getId();

        MusicUpdateRequestDto requestDto = MusicUpdateRequestDto.builder()
                .title("새로운 제목")
                .composer("새로운 composer")
                .songwriter("새로운 writer")
                .albumId(album2.getId())
                .build();

        HttpEntity<MusicUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        String url = "http://localhost:" + port + "/v1/musics/" + id;


        // when
        ResponseEntity<Object> responseEntity = restTemplate
                .exchange(url, HttpMethod.PATCH, requestEntity, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void music_update_relation_add() {

        // given
        Music music = musicRepository.save(Music.builder()
                .title("music")
                .composer("composer")
                .songwriter("writer")
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
                .artist(artist1)
                .music(music)
                .build()
        );
        /**
         * 원래 연결된 아티스트 리스트 : [artist1]
         * artist2를 새로 연결하는 과정
         */

        Long id = music.getId();
        List<Long> artistIdList = new ArrayList<>();
        artistIdList.add(artist2.getId());

        MusicUpdateRelationRequestDto requestDto = MusicUpdateRelationRequestDto.builder()
                .artistIdList(artistIdList)
                .build();

        HttpEntity<MusicUpdateRelationRequestDto> requestEntity = new HttpEntity<>(requestDto);
        String url = "http://localhost:" + port + "/v1/musics/artist-add/" + id;

        // when
        ResponseEntity<Object> responseEntity = restTemplate
                .exchange(url, HttpMethod.PATCH, requestEntity, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void music_update_relation_delete() {

        // given
        Music music = musicRepository.save(Music.builder()
                .title("music")
                .composer("composer")
                .songwriter("writer")
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
                .artist(artist1)
                .music(music)
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .artist(artist2)
                .music(music)
                .build()
        );
        /**
         * 원래 연결된 아티스트 리스트 : [artist1, artist2]
         * artist2를 연결 해제하는 과정
         */

        Long id = music.getId();
        List<Long> artistIdList = new ArrayList<>();
        artistIdList.add(artist2.getId());

        MusicUpdateRelationRequestDto requestDto = MusicUpdateRelationRequestDto.builder()
                .artistIdList(artistIdList)
                .build();

        HttpEntity<MusicUpdateRelationRequestDto> requestEntity = new HttpEntity<>(requestDto);
        String url = "http://localhost:" + port + "/v1/musics/artist-delete/" + id;

        // when
        ResponseEntity<Object> responseEntity = restTemplate
                .exchange(url, HttpMethod.PATCH, requestEntity, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void music_delete() {

        // given
        Music music = musicRepository.save(Music.builder()
                .title("music")
                .composer("composer")
                .songwriter("songwriter")
                .build()
        );

        Long id = music.getId();
        String url = "http://localhost:" + port + "/v1/musics/" + id;

        // when
        restTemplate.delete(url);

        // then
        Music testMusic = musicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음악입니다."));
        assertThat(testMusic.getDeletedAt()).isNotNull();
    }
}
