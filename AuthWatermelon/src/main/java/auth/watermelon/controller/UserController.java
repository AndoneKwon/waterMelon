package auth.watermelon.controller;

import auth.watermelon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/v1/sign-up")
    public Long join(@RequestBody Map<String, String> user) {
        return userService.join(user);
    }

    // 로그인
    @PostMapping("/v1/sign-in")
    public String login(@RequestBody Map<String, String> user) {
        return userService.login(user);
    }

    // 테스트 api
    @GetMapping("/v1/test")
    public String test() {
        return "Hello world!";
    }
}
