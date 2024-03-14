package com.psychology.product.diagnostic.interpretation;

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

public class InterpretationDAO {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "interpretation_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "interpretation_text")
    private String interpretationText;

}
