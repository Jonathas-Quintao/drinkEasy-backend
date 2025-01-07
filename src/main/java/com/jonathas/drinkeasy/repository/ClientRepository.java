package com.jonathas.drinkeasy.repository;

import com.jonathas.drinkeasy.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
