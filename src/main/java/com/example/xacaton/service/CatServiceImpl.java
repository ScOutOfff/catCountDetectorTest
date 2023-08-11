package com.example.xacaton.service;

import com.example.xacaton.model.Cat;
import com.example.xacaton.repository.CatRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class CatServiceImpl implements CatService {

    private final CatRepo catRepo;

    @Autowired
    public CatServiceImpl(CatRepo catRepo) {
        this.catRepo = catRepo;
    }

    @Override
    public void saveCatInDB(Cat cat) {
        catRepo.save(cat);
    }

    @Override
    public Cat getCatFromDB(Long id) {
        return catRepo.getCatById(id);
    }
}
