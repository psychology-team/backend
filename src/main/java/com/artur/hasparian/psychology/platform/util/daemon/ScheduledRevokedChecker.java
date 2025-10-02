package com.artur.hasparian.psychology.platform.util.daemon;

import com.artur.hasparian.psychology.platform.repository.UserRepository;
import com.artur.hasparian.psychology.platform.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ScheduledRevokedChecker {
    private final UserRepository userRepository;
    @Value("${application.user.expiration.time}")
    private long expirationTime;

    @Scheduled(fixedRateString = "${application.daemon.schedule.repeatTime}")
    private void checkRevokedUserExpiration() {
        getExpirationUsers().ifPresent(userRepository::deleteAll);
    }

    private Optional<List<User>> getExpirationUsers() {
        Instant thresholdTime = Instant.now().minusMillis(expirationTime);
        return userRepository.findAllByRevokedTimestampLessThanEqual(thresholdTime);

    }
}
