package com.psychology.product.repository;

import com.psychology.product.repository.model.MakCardDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MacCardRepository extends JpaRepository<MakCardDAO, UUID> {
}
