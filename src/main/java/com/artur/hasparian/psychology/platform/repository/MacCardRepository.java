package com.artur.hasparian.psychology.platform.repository;

import com.artur.hasparian.psychology.platform.repository.model.MakCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MacCardRepository extends JpaRepository<MakCard, UUID> {
}
