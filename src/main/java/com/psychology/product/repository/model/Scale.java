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
@Table(name = "scales")

public class Scale {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "scale_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "scale_name")
    private String scaleName;

}
