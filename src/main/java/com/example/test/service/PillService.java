package com.example.test.service;

import com.example.test.model.Pill;
import com.example.test.repository.PillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PillService {
    @Autowired
    private PillRepository pillRepository;

    public Pill savePill(Pill pill) {
        return pillRepository.save(pill);
    }
}
