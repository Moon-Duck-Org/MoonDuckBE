package moonduck.server.service;

import lombok.RequiredArgsConstructor;
import moonduck.server.dto.UserEditDTO;
import moonduck.server.dto.UserLoginDTO;
import moonduck.server.entity.User;
import moonduck.server.exception.UserNotFoundException;
import moonduck.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public boolean tryRegistration(UserLoginDTO userDto) {
        if (userRepository.existsByDeviceId(userDto.getDeviceId())) {
            return false;   // 회원가입 수행 안됨
        } else {
            User newUser = new User();
            newUser.setDeviceId(userDto.getDeviceId());

            userRepository.save(newUser);

            return true;
        }
    }

    @Transactional
    public User editNickname(UserEditDTO userEditInfo) {
        User user = userRepository.findByDeviceId(userEditInfo.getDeviceId())
                .orElseThrow(() -> new UserNotFoundException());

        user.setNickname(userEditInfo.getNickname());
        userRepository.save(user);

        return user;
    }

    public User getUser(String deviceId) {
        return userRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new UserNotFoundException());
    }
}
