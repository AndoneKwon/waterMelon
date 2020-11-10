package com.watermelon.domain.music;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {

    List<Music> findByTitleContaining(String Title);
}
