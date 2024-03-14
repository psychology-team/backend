package com.psychology.product.diagnostic.userdiagnosticresult;

import com.psychology.product.diagnostic.diagnostic.DiagnosticDAO;
import com.psychology.product.user.UserDAO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_diagnostic_results")

public class UserDiagnosticResultDAO {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "user_diagnostic_result_id", updatable = false, nullable = false)
    private UUID userDiagnosticResultId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDAO userDAO;

    @OneToOne
    @JoinColumn(name = "diagnostic_id")
    private DiagnosticDAO diagnosticDAO;

    @Column(name = "scale_points")
    private short scalePoints;

    @Column(name = "interpretation_points")
    private short interpretationPoints;

}
