package com.watermelon.dto.artist_album;

import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.dto.album.AlbumOnlyResponseDto;
import lombok.Getter;

/**
 * 아티스트-앨범 관계 테이블에서 관계 매핑 정보를 제외한 앨범만을 필터링하기 위한 Dto
 */

@Getter
public class ArtistToAlbumResponseDto {

    private AlbumOnlyResponseDto album;

    public ArtistToAlbumResponseDto(ArtistAlbum entity) {
        this.album = new AlbumOnlyResponseDto(entity.getAlbum());
    }
}
