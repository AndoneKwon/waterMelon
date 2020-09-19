package com.watermelon.domain.album;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}
