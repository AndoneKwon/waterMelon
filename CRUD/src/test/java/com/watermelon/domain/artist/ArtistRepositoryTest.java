package com.watermelon.domain.artist;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.album.AlbumRepository;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.domain.artist_album.ArtistAlbumRepository;
import com.watermelon.domain.artist_music.ArtistMusic;
import com.watermelon.domain.artist_music.ArtistMusicRepository;
import com.watermelon.domain.music.Music;
import com.watermelon.domain.music.MusicRepository;
import com.watermelon.dto.artist.ArtistUpdateRelationRequestDto;
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

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private ArtistMusicRepository artistMusicRepository;

    @After
    public void cleanup() {
        artistAlbumRepository.deleteAll();
        artistMusicRepository.deleteAll();
        musicRepository.deleteAll();
        artistRepository.deleteAll();
        albumRepository.deleteAll();
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
                .isGroup(true)
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
        Music music = musicRepository.save(Music.builder()
                .title("music")
                .composer("composer")
                .songwriter("songwriter")
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .artist(artist)
                .music(music)
                .build()
        );

        Long id = artist.getId();
        String url = "http://localhost:" + port + "/v1/artists/" + id;

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
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
        Music music1 = musicRepository.save(Music.builder()
                .title("music1")
                .composer("composer")
                .songwriter("songwriter")
                .build()
        );
        Music music2 = musicRepository.save(Music.builder()
                .title("music2")
                .composer("composer")
                .songwriter("songwriter")
                .build()
        );
        Music music3 = musicRepository.save(Music.builder()
                .title("music3")
                .composer("composer")
                .songwriter("songwriter")
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .artist(artist1)
                .music(music1)
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .artist(artist1)
                .music(music2)
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .artist(artist2)
                .music(music3)
                .build()
        );

        String url = "http://localhost:" + port + "/v1/artists";

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
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
        Music music1 = musicRepository.save(Music.builder()
                .title("music1")
                .composer("composer")
                .songwriter("songwriter")
                .build()
        );
        Music music2 = musicRepository.save(Music.builder()
                .title("music2")
                .composer("composer")
                .songwriter("songwriter")
                .build()
        );
        Music music3 = musicRepository.save(Music.builder()
                .title("music3")
                .composer("composer")
                .songwriter("songwriter")
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .artist(artist1)
                .music(music1)
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .artist(artist1)
                .music(music2)
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .artist(artist2)
                .music(music3)
                .build()
        );

        String keyword = "t1";
        String url = "http://localhost:" + port + "/v1/artists?keyword=" + keyword;

        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
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

        Long id = artist.getId();

        String expectedName = "updated name";

        ArtistUpdateRequestDto requestDto = ArtistUpdateRequestDto.builder()
                .name("updated name")
                .artistId(group2.getId())
                .build();

        HttpEntity<ArtistUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        String url = "http://localhost:" + port + "/v1/artists/" + id;

        // when
        ResponseEntity<Object> responseEntity = restTemplate
                .exchange(url, HttpMethod.PATCH, requestEntity, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Artist updatedArtist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));
        assertThat(updatedArtist.getName()).isEqualTo(expectedName);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void artistUpdateRelationAlbumAdd() {

        // given
        Artist artist = artistRepository.save(Artist.builder()
                .name("artist")
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
                .artist(artist)
                .album(album1)
                .build()
        );

        /**
         * 원래 연결된 앨범 리스트 : [album1]
         * album2를 새로 연결하는 과정
         */

        Long id = artist.getId();
        List<Long> albumIdList = new ArrayList<>();
        albumIdList.add(album2.getId());

        ArtistUpdateRelationRequestDto requestDto = ArtistUpdateRelationRequestDto.builder()
                .albumIdList(albumIdList)
                .build();

        HttpEntity<ArtistUpdateRelationRequestDto> requestEntity = new HttpEntity<>(requestDto);
        String url = "http://localhost:" + port + "/v1/artists/album-add/" + id;

        // when
        ResponseEntity<Object> responseEntity = restTemplate
                .exchange(url, HttpMethod.PATCH, requestEntity, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void artistUpdateRelationAlbumDelete() {

        // given
        Artist artist = artistRepository.save(Artist.builder()
                .name("artist")
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
                .artist(artist)
                .album(album1)
                .build()
        );
        artistAlbumRepository.save(ArtistAlbum.builder()
                .artist(artist)
                .album(album2)
                .build()
        );

        /**
         * 원래 연결된 앨범 리스트 : [album1, album2]
         * album2를 연결 해제하는 과정
         */

        Long id = artist.getId();
        List<Long> albumIdList = new ArrayList<>();
        albumIdList.add(album2.getId());

        ArtistUpdateRelationRequestDto requestDto = ArtistUpdateRelationRequestDto.builder()
                .albumIdList(albumIdList)
                .build();

        HttpEntity<ArtistUpdateRelationRequestDto> requestEntity = new HttpEntity<>(requestDto);
        String url = "http://localhost:" + port + "/v1/artists/album-delete/" + id;

        // when
        ResponseEntity<Object> responseEntity = restTemplate
                .exchange(url, HttpMethod.PATCH, requestEntity, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void artistUpdateRelationMusicAdd() {

        // given
        Artist artist = artistRepository.save(Artist.builder()
                .name("artist")
                .build()
        );
        Music music1 = musicRepository.save(Music.builder()
                .title("music1")
                .composer("composer")
                .songwriter("songwriter")
                .build()
        );
        Music music2 = musicRepository.save(Music.builder()
                .title("music2")
                .composer("composer")
                .songwriter("songwriter")
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .artist(artist)
                .music(music1)
                .build()
        );

        /**
         * 원래 연결된 앨범 리스트 : [music1]
         * music2를 새로 연결하는 과정
         */

        Long id = artist.getId();
        List<Long> musicIdList = new ArrayList<>();
        musicIdList.add(music2.getId());

        ArtistUpdateRelationRequestDto requestDto = ArtistUpdateRelationRequestDto.builder()
                .musicIdList(musicIdList)
                .build();

        HttpEntity<ArtistUpdateRelationRequestDto> requestEntity = new HttpEntity<>(requestDto);
        String url = "http://localhost:" + port + "/v1/artists/music-add/" + id;

        // when
        ResponseEntity<Object> responseEntity = restTemplate
                .exchange(url, HttpMethod.PATCH, requestEntity, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void artistUpdateRelationMusicDelete() {

        // given
        Artist artist = artistRepository.save(Artist.builder()
                .name("artist")
                .build()
        );
        Music music1 = musicRepository.save(Music.builder()
                .title("music1")
                .composer("composer")
                .songwriter("songwriter")
                .build()
        );
        Music music2 = musicRepository.save(Music.builder()
                .title("music2")
                .composer("composer")
                .songwriter("songwriter")
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .artist(artist)
                .music(music1)
                .build()
        );
        artistMusicRepository.save(ArtistMusic.builder()
                .artist(artist)
                .music(music2)
                .build()
        );

        /**
         * 원래 연결된 앨범 리스트 : [music1, music2]
         * music2를 연결 해제하는 과정
         */

        Long id = artist.getId();
        List<Long> musicIdList = new ArrayList<>();
        musicIdList.add(music2.getId());

        ArtistUpdateRelationRequestDto requestDto = ArtistUpdateRelationRequestDto.builder()
                .musicIdList(musicIdList)
                .build();

        HttpEntity<ArtistUpdateRelationRequestDto> requestEntity = new HttpEntity<>(requestDto);
        String url = "http://localhost:" + port + "/v1/artists/music-delete/" + id;

        // when
        ResponseEntity<Object> responseEntity = restTemplate
                .exchange(url, HttpMethod.PATCH, requestEntity, Object.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
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
