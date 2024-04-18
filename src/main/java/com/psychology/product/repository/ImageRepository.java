package com.psychology.product.repository;

import com.psychology.product.repository.model.ImageDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<ImageDAO, UUID> {
}
