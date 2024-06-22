package com.psychology.product.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = "questionsList")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diagnostics")

public class Diagnostic implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "diagnostic_id", updatable = false, nullable = false)
    private UUID diagnosticId;

    @Column(name = "diagnostic_name")
    private String diagnosticName;

    @Column(name = "diagnostic_description")
    private String diagnosticDescription;

    @OneToMany(mappedBy = "diagnostic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questionsList;

}