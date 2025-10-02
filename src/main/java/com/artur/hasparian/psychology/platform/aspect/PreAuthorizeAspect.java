package com.artur.hasparian.psychology.platform.aspect;

import com.artur.hasparian.psychology.platform.repository.UserRepository;
import com.artur.hasparian.psychology.platform.repository.model.User;
import com.artur.hasparian.psychology.platform.service.JwtUtils;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PreAuthorizeAspect {
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Before("execution(* com.artur.hasparian.psychology.platform.controller..*Controller.*(..))&& !@annotation(com.artur.hasparian.psychology.platform.aspect.ExcludeAspect)")
    public void checkUserStatus(JoinPoint joinPoint) throws AuthException {
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorizationHeader = servletRequest.getHeader("Authorization");
        String email = checkRevokedAndGetEmail(authorizationHeader);
        String endpoint = joinPoint.getSignature().toShortString();
        Object payload = joinPoint.getArgs().length > 0 ? joinPoint.getArgs()[0] : null;
        log.info("User \u001B[34m{}\u001B[0m accessed endpoint \u001B[34m{}\u001B[0m with payload: {{}}", email, endpoint, payload);
    }

    private String checkRevokedAndGetEmail(String authorizationHeader) throws AuthException {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtUtils.getEmailFromJwtToken(token);
            User user = userRepository.findByEmail(email).orElseThrow(() -> new AuthException("Unauthorized"));
            if (user.getRevoked()) throw new AuthException("Unauthorized");
            return email;

        } else throw new AuthException("Unauthorized");
    }
}
