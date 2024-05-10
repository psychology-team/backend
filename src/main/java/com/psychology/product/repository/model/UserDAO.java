package com.psychology.product.repository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serializable;
import java.sql.Types;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class UserDAO implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "revoked")
    private Boolean revoked;

    @Column(name = "revoked_timestamp")
    private Instant revokedTimestamp;

    @Column(name = "created_timestamp")
    private Instant createdTimestamp;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(Types.ARRAY)
    private Set<UserAuthority> authorities;

    @Transient
    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
    private List<DiagnosticResultDAO> diagnosticResultDAOList;

}
