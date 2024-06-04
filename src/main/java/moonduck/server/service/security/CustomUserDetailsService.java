package moonduck.server.service.security;

import lombok.RequiredArgsConstructor;
import moonduck.server.dto.CustomUserDetails;
import moonduck.server.entity.User;
import moonduck.server.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String deviceId) throws UsernameNotFoundException {
        User user = userRepository.findByDeviceId(deviceId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setDeviceId(deviceId);
                    return userRepository.save(newUser);
                });

        return new CustomUserDetails(user);
    }
}
