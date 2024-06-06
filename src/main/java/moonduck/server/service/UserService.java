package moonduck.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.query.CategoryCountDTO;
import moonduck.server.dto.request.UserEditRequest;
import moonduck.server.dto.response.UserInfoResponse;
import moonduck.server.dto.request.LoginRequest;
import moonduck.server.enums.Category;
import moonduck.server.entity.User;
import moonduck.server.exception.ErrorCode;
import moonduck.server.exception.ErrorException;
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
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User tryRegistrationAndReturnUser(LoginRequest userDto) {
        String snsId = userDto.getDvsnCd() + userDto.getId();

        return userRepository.findBySnsId(snsId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setSnsId(snsId);
                    return userRepository.save(newUser);
                });
    }

    @Transactional
    public User editNickname(Long userId, String nickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));

        if (userRepository.existsByNickname(nickname)) {
            throw new ErrorException(ErrorCode.NICKNAME_DUPLICATE);
        }

        user.setNickname(nickname);

        return user;
    }

    public UserInfoResponse getUser(Long userId) {
        List<CategoryCountDTO> objects = userRepository.countByCategoryAndUserId(userId);

        Map<String, Long> countByCategory = new HashMap<>();
        for (Category category : EnumSet.allOf(Category.class)) {
            countByCategory.put(category.name(), 0L);
        }

        for (CategoryCountDTO dto : objects) {
            countByCategory.put(dto.getCategory().name(), dto.getCount());
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));

        UserInfoResponse userInfoDTO = new UserInfoResponse(user);

        userInfoDTO.setMOVIE(countByCategory.get("MOVIE"));
        userInfoDTO.setBOOK(countByCategory.get("BOOK"));
        userInfoDTO.setDRAMA(countByCategory.get("DRAMA"));
        userInfoDTO.setCONCERT(countByCategory.get("CONCERT"));

        return userInfoDTO;
    }

}
