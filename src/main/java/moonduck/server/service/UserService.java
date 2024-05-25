package moonduck.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.UserEditDTO;
import moonduck.server.dto.UserInfoDTO;
import moonduck.server.dto.UserLoginDTO;
import moonduck.server.entity.Category;
import moonduck.server.entity.User;
import moonduck.server.exception.NicknameDuplicateException;
import moonduck.server.exception.UserNotFoundException;
import moonduck.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
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

    public UserInfoDTO getUser(String deviceId) {
        List<Object[]> objects = userRepository.countByCategoryAndUserId(deviceId);

        Map<String, Long> countByCategory = new HashMap<>();
        for (Category category : EnumSet.allOf(Category.class)) {
            countByCategory.put(category.name(), 0L);
        }

        for (Object[] result : objects) {
            String category = ((Category) result[0]).name();
            Long count = (Long) result[1];
            countByCategory.put(category, count);
        }

        User user = userRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new UserNotFoundException());

        UserInfoDTO userInfoDTO = new UserInfoDTO(user);

        userInfoDTO.setMOVIE(countByCategory.get("MOVIE"));
        userInfoDTO.setBOOK(countByCategory.get("BOOK"));
        userInfoDTO.setDRAMA(countByCategory.get("DRAMA"));
        userInfoDTO.setCONCERT(countByCategory.get("CONCERT"));

        return userInfoDTO;
    }

}
