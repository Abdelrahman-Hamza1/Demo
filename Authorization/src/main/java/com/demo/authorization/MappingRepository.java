package com.demo.authorization;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface MappingRepository extends JpaRepository<Mapping,Integer> {
    Optional<Mapping> findByUsername(String username);
    void deleteByUsername(String username);
}
