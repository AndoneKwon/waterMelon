package com.watermelon.dto.album;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AlbumUpdateRelationRequestDto {
    private List<Long> artistIdList;

    @Builder
    public AlbumUpdateRelationRequestDto(List<Long> artistIdList) {
        this.artistIdList = artistIdList;
    }
}
