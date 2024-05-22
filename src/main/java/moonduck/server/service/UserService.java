package moonduck.server.service;

import lombok.RequiredArgsConstructor;
import moonduck.server.dto.UserLoginDTO;
import moonduck.server.entity.User;
import moonduck.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Boolean tryRegistration(UserLoginDTO userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return false;   // 회원가입 수행 안됨
        } else {
            User newUser = new User();
            newUser.setName(userDto.getName());
            newUser.setEmail(userDto.getEmail());

            userRepository.save(newUser);

            return true;
        }
    }
}
