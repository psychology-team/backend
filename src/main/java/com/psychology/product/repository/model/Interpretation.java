package com.psychology.product.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interpretations")

public class Interpretation {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "interpretation_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "interpretation_text")
    private String interpretationText;

}
