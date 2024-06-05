package moonduck.server.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import moonduck.server.dto.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/user/login");
    }

    public LoginFilter(AuthenticationManager authenticationManager, AuthenticationManager authenticationManager1, JWTUtil jwtUtil) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager1;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String deviceId = obtainDeviceId(request);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(deviceId, "", null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
//        String deviceId = customUserDetails.getUsername();
//
//        String token = jwtUtil.createJwt(deviceId, 60 * 60 * 10L);
//        response.addHeader("Authorization", "Bearer " + token);

        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }

    protected String obtainDeviceId(HttpServletRequest request) {
        System.out.println(request.getParameter("deviceId"));
        return request.getParameter("deviceId");
    }
}
