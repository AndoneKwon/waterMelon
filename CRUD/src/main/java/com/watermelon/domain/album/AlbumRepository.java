package com.watermelon.domain.album;

import com.watermelon.domain.music.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByTitleContaining(String Title);
}
