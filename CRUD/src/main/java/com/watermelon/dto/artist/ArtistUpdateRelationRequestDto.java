package com.watermelon.dto.artist;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ArtistUpdateRelationRequestDto {
    private List<Long> albumIdList;
    private List<Long> musicIdList;

    @Builder
    public ArtistUpdateRelationRequestDto(List<Long> albumIdList, List<Long> musicIdList) {
        this.albumIdList = albumIdList;
        this.musicIdList = musicIdList;
    }
}
