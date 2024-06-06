package moonduck.server.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import moonduck.server.dto.auth.UserDTO;
import moonduck.server.exception.ErrorCode;
import moonduck.server.exception.auth.TokenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorization.split(" ")[1];

        validateAccessToken(accessToken);

        UserDTO userDTO = new UserDTO(jwtUtil.getDeviceId(accessToken));

        Authentication authentication = new JWTTokenAuthentication(accessToken, userDTO);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private void validateAccessToken(String accessToken) {
        if (
                !jwtUtil.isValidToken(accessToken) ||
                        !jwtUtil.getCategory(accessToken).equals("access") ||
                        jwtUtil.isExpired(accessToken)
        ) {
            throw new TokenException(ErrorCode.INVALID_TOKEN);
        }
    }
}
