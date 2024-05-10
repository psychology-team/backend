package com.psychology.product.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diagnostic_results")

public class DiagnosticResultDAO implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "diagnostic_result_id", updatable = false, nullable = false)
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
