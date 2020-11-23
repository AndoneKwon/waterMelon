package auth.watermelon.auth;

import auth.watermelon.domain.user.UserRepository;
import auth.watermelon.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }
}
