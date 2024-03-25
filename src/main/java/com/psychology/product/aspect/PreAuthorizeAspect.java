package com.psychology.product.aspect;

import com.psychology.product.repository.UserRepository;
import com.psychology.product.repository.model.UserDAO;
import com.psychology.product.service.JwtUtils;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class PreAuthorizeAspect {
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Before("execution(* com.psychology.product.controller..*Controller.*(..))&& !@annotation(com.psychology.product.aspect.ExcludeAspect)")
    public void checkUserStatus() throws AuthException {
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorizationHeader = servletRequest.getHeader("Authorization");
        checkRevoked(authorizationHeader);
    }

    private void checkRevoked(String authorizationHeader) throws AuthException {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtUtils.getEmailFromJwtToken(token);
            UserDAO userDAO = userRepository.findByEmail(email).orElseThrow(() -> new AuthException("Unauthorized"));
            if (userDAO.getRevoked())
                throw new AuthException("Unauthorized");

        } else throw new AuthException("Unauthorized");
    }
}
