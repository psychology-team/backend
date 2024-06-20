package com.psychology.product.repository;

import com.psychology.product.repository.model.MakCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MacCardRepository extends JpaRepository<MakCard, UUID> {
}
