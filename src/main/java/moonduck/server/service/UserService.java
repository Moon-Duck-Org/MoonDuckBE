package moonduck.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.query.CategoryCountDTO;
import moonduck.server.dto.request.LoginRequest;
import moonduck.server.dto.response.UserIdResponse;
import moonduck.server.dto.response.UserInfoResponse;
import moonduck.server.dto.response.UserPushResponse;
import moonduck.server.dto.response.UserResponse;
import moonduck.server.entity.Board;
import moonduck.server.entity.User;
import moonduck.server.enums.Category;
import moonduck.server.exception.ErrorCode;
import moonduck.server.exception.ErrorException;
import moonduck.server.repository.BoardRepository;
import moonduck.server.repository.UserRepository;
import moonduck.server.service.s3.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final S3Service s3Service;

    @Transactional
    public User tryRegistrationAndReturnUser(LoginRequest userDto) {
        String snsId = userDto.getDvsnCd() + userDto.getId();

        return userRepository.findBySnsId(snsId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setSnsId(snsId);
                    newUser.setPush("Y");
                    return userRepository.save(newUser);
                });
    }

    @Transactional
    public UserResponse editNickname(Long userId, String nickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));

        if (userRepository.existsByNickname(nickname)) {
            throw new ErrorException(ErrorCode.NICKNAME_DUPLICATE);
        }

        user.setNickname(nickname);

        UserResponse userResponse = UserResponse.from(user);

        return userResponse;
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

    @Transactional
    public UserIdResponse deleteUser(Long userId) {
        List<Board> boards = boardRepository.findAllByUserId(userId);

        List<String> images = boards.stream()
                .flatMap(board -> Stream.of(board.getImage1(), board.getImage2(), board.getImage3(), board.getImage4(), board.getImage5()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        s3Service.deleteFiles(images);

        boardRepository.deleteAll(boards);
        userRepository.deleteById(userId);

        return new UserIdResponse(userId);
    }

    @Transactional
    public UserPushResponse editPush(Long userId, String push) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));

        user.setPush(push);

        UserPushResponse userPushResponse = UserPushResponse.from(user);

        return userPushResponse;
    }

}
