package com.artur.hasparian.psychology.platform.repository;

import com.artur.hasparian.psychology.platform.repository.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}
