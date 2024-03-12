package com.psychology.product.test.entity.scale;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scales")

public class ScaleDAO {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "scale_id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "scale_name")
    private String scaleName;

}
