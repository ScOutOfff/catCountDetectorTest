package com.example.xacaton.repository;

import com.example.xacaton.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepo extends JpaRepository<Cat, Long> {
    Cat getCatById(Long id);
}
