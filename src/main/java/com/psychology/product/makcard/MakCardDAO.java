package com.psychology.product.makcard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mak_cards")
public class MakCardDAO {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "mak_card_id", updatable = false, nullable = false)
    private UUID makCardId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;
}
