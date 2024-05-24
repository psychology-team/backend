package com.psychology.product.repository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tokens")
public class TokenDAO {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "token_id", updatable = false, nullable = false)
    UUID id;

    @Column(name = "token", updatable = false, nullable = false, unique = true)
    String token;

    @Column(name = "revoked")
    boolean revoked;

    @Column(name = "expired")
    boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    UserDAO user;

    @Column(name = "updated_timestamp")
    private Instant updatedTimestamp;
}
