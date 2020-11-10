package com.watermelon.controller;

import com.watermelon.domain.user.User;
import com.watermelon.service.UserService;
import com.watermelon.dto.user.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 사용자 리스트
    @GetMapping("/v1/users")
    public List<User> list() {
        return userService.list();
    }

    // 사용자 프로필 조회
    @GetMapping("/v1/profile")
    public User profile(@RequestParam Long id) {
        return userService.profile(id);
    }

    // 사용자 프로필 수정
    @PatchMapping("/v1/profile/{id}")
    public User update(@PathVariable Long id, @RequestBody UserUpdateRequestDto requestDto) {
        return userService.update(id, requestDto);
    }

    // 사용자 프로필 삭제
    @DeleteMapping("/v1/profile/{id}")
    public String delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}
