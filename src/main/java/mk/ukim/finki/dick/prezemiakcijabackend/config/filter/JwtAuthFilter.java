package mk.ukim.finki.dick.prezemiakcijabackend.config.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.ukim.finki.dick.prezemiakcijabackend.config.JwtAuthConstants;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.RequestDetailsDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtAuthFilter extends BasicAuthenticationFilter {

    private final JWTVerifier verifier;

    public JwtAuthFilter(AuthenticationManager authenticationManager, JWTVerifier verifier) {
        super(authenticationManager);
        this.verifier = verifier;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        return (path.equals("/api/users/sign-in") || path.equals("/api/users/register"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtAuthConstants.HEADER_STRING);

        if (header == null || !header.startsWith(JwtAuthConstants.TOKEN_PREFIX)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing auth information.");
            return;
        }

        String jwtToken = header.replace(JwtAuthConstants.TOKEN_PREFIX, "");

        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            String subject = decodedJWT.getSubject();

            RequestDetailsDto userDetails = new ObjectMapper().readValue(subject, RequestDetailsDto.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails.getId(), null, Collections.singleton(userDetails.getRole())
            );

            SecurityContextHolder.getContext().setAuthentication(token);
            chain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token.");
            return;
        }
    }
}
