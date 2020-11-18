package com.watermelon.dto.music;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MusicUpdateRelationRequestDto {
    private List<Long> artistIdList;

    @Builder
    public MusicUpdateRelationRequestDto(List<Long> artistIdList) {
        this.artistIdList = artistIdList;
    }
}
