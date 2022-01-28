package com.igor.company.repository;

import com.igor.company.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    int countByRG(String RG);

    int countById(Long id);
}
