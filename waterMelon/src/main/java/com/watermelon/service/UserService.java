package com.watermelon.service;

import com.watermelon.domain.user.User;
import com.watermelon.domain.user.UserRepository;
import com.watermelon.dto.user.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    // 사용자 리스트 출력
    public List<User> list() {
        List<User> users = userRepository.findAll();
        return users;
    }

    // 사용자 프로필 조회
    public User profile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        return user;
    }

    @Transactional
    // 사용자 프로필 수정
    public User update(Long id, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        user.update(requestDto);

        return user;
    }

    @Transactional
    // 사용자 프로필 삭제
    public String delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다"));

        Date now = new Date();

        user.delete(now);

        return "삭제되었습니다.";
    }
}
