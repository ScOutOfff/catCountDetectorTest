package com.example.xacaton.service;

import com.example.xacaton.model.Cat;
import org.springframework.stereotype.Service;

@Service
public interface CatService {
    void saveCatInDB(Cat cat);
    Cat getCatFromDB(Long id);
}
