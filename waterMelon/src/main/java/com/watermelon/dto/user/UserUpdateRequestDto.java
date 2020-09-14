package com.watermelon.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String email;
    private String nickname;
    private String profile;

    @Builder
    public UserUpdateRequestDto(String email, String nickname, String profile) {
        this.email = email;
        this.nickname = nickname;
        this.profile = profile;
    }
}
