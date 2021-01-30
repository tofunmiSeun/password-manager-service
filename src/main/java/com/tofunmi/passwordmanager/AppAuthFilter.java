package com.tofunmi.passwordmanager;

import com.tofunmi.passwordmanager.user.User;
import com.tofunmi.passwordmanager.user.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created By tofunmi on 08/01/2021
 */
@Component
public class AppAuthFilter extends GenericFilterBean {
    private final UserService userService;

    public AppAuthFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String encodedBasicToken = resolveToken((HttpServletRequest) request);
        if (encodedBasicToken != null) {
            Optional<Authentication> auth = getAuthentication(encodedBasicToken);
            auth.ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Basic ")) {
            String token = bearerToken.replace("Basic ", "");
            if (!Objects.equals(token, "null")) {
                return token;
            }
        }
        return null;
    }

    private Optional<Authentication> getAuthentication(String encodedBasicToken) {
        String decodedToken = new String(Base64.getDecoder().decode(encodedBasicToken));
        String[] tokenParts = decodedToken.split(":");
        if (tokenParts.length == 2) {
            String emailAddress = tokenParts[0];
            String password = tokenParts[1];

            Optional<User> user = userService.findByEmailAddress(emailAddress);
            if (user.isPresent() && userService.passwordMatches(user.get(), password)) {
                return user.map(e -> new UsernamePasswordAuthenticationToken(emailAddress, encodedBasicToken, Collections.emptyList()));
            }
        }

        return Optional.empty();
    }
}
