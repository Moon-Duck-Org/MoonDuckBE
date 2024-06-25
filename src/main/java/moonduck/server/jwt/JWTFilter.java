package moonduck.server.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import moonduck.server.dto.auth.UserDTO;
import moonduck.server.exception.ErrorCode;
import moonduck.server.exception.ErrorException;
import moonduck.server.exception.ErrorResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorization = request.getHeader("Authorization");

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new ErrorException(ErrorCode.INVALID_TOKEN);
            }

            String accessToken = authorization.split(" ")[1];

            validateAccessToken(accessToken);

            UserDTO userDTO = new UserDTO(jwtUtil.getUserId(accessToken));

            Authentication authentication = new JWTTokenAuthentication(accessToken, userDTO);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (ErrorException ex) {
            ErrorCode errorCode = ex.getErrorCode();

            response.setStatus(errorCode.getStatus());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            String body = objectMapper.writeValueAsString(ErrorResponse.of(errorCode));
            response.getWriter().write(body);
        }
    }

    private void validateAccessToken(String accessToken) {
        if (jwtUtil.isExpired(accessToken)) {
            throw new ErrorException(ErrorCode.TOKEN_EXPIRED);
        }else if (!jwtUtil.isValidToken(accessToken) || !jwtUtil.getCategory(accessToken).equals("access")) {
            throw new ErrorException(ErrorCode.INVALID_TOKEN);
        }
    }
}