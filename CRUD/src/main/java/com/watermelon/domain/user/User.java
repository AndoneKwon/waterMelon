package com.watermelon.domain.user;

import com.watermelon.domain.BaseTimeEntity;
import com.watermelon.dto.user.UserUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String email;
    private String nickname;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100)
    private String profile;

    @Column(length = 8)
    private String salt;

    private Byte role;

    private Date deleted_at;

    @Builder
    public User(String email, String nickname, String password,
                String profile, String salt, Byte role, Date deleted_at) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.profile = profile;
        this.salt = salt;
        this.role = role;
        this.deleted_at = deleted_at;
    }

    /**
     * 수정 기능
     * patch를 구현하기 위해 널 값을 체크하고 수정할 값이 들어있는
     * 필드만 수정 처리
      */
    public void update(UserUpdateRequestDto requestDto) {
        if (requestDto.getEmail() != null) {
            this.email = requestDto.getEmail();
        }
        if (requestDto.getNickname() != null) {
            this.nickname = requestDto.getNickname();
        }
        if (requestDto.getProfile() != null) {
            this.profile = requestDto.getProfile();
        }
    }

    // 삭제 기능
    public void delete(Date now) {
        this.deleted_at = now;
    }
}
