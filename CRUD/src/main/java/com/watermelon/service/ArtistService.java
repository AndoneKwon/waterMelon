package com.watermelon.service;

import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.artist.ArtistRepository;
import com.watermelon.dto.artist.ArtistResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private List<ArtistResponseDto> responseDtos;

    // 아티스트 개별 조회
    public ArtistResponseDto read(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));

        return new ArtistResponseDto(artist);
    }

    // 아티스트 목록 조회
    public List<ArtistResponseDto> list() {
        List<Artist> artists = artistRepository.findAll();

        for (Artist artist : artists) {
            responseDtos.add(new ArtistResponseDto(artist));
        }

        return responseDtos;
    }

    // 아티스트 수정

    // 아티스트 삭제
}
