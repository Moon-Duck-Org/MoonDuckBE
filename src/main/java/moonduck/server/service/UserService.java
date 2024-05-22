package moonduck.server.service;

import lombok.RequiredArgsConstructor;
import moonduck.server.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
}
