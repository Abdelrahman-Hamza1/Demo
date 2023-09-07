package com.demo.authorservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorSubscription,Integer> {
    List<AuthorSubscription> findAllByAuthorName(String authorName);
}
