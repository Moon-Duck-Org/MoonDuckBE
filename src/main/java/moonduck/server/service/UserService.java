package moonduck.server.service;

import lombok.RequiredArgsConstructor;
import moonduck.server.dto.UserEditDTO;
import moonduck.server.dto.UserLoginDTO;
import moonduck.server.entity.User;
import moonduck.server.exception.NicknameDuplicateException;
import moonduck.server.exception.UserNotFoundException;
import moonduck.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User tryRegistrationAndReturnUser(UserLoginDTO userDto) {
        return userRepository.findByDeviceId(userDto.getDeviceId())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setDeviceId(userDto.getDeviceId());
                    return userRepository.save(newUser);
                });
    }

    @Transactional
    public User editNickname(UserEditDTO userEditInfo) {
        User user = userRepository.findByDeviceId(userEditInfo.getDeviceId())
                .orElseThrow(() -> new UserNotFoundException());

        if (userRepository.existsByNickname(userEditInfo.getNickname())) {
            throw new NicknameDuplicateException();
        }

        user.setNickname(userEditInfo.getNickname());
        userRepository.save(user);

        return user;
    }

    public User getUser(String deviceId) {
        return userRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new UserNotFoundException());
    }
}
