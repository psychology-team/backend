package com.psychology.product.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "image_id", referencedColumnName = "image_id")
    private ImageDAO previewImage;

    @ManyToMany
    @JoinTable(
            name = "mak_card_images",
            joinColumns = @JoinColumn(name = "mac_card_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<ImageDAO> images;
}
