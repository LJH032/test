package com.example.test.service;

import com.example.test.model.Pill;
import com.example.test.repository.PillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PillService {

    @Autowired
    private PillRepository pillRepository;

    // Pill 저장 메서드
    public Pill savePill(Pill pill) {
        return pillRepository.save(pill);  // Pill을 DB에 저장
    }

    // drugNum으로 Pill을 조회하는 메서드
    public Optional<Pill> findPillByDrugNum(Integer drugNum) {
        // findByDrugNum 메서드를 호출하여 Pill 조회, null일 경우 Optional로 감싸서 반환
        return Optional.ofNullable(pillRepository.findByDrugNum(drugNum));
    }

    // Pill 업데이트 메서드
    public Pill updatePill(Pill pill) {
        // drugNum이 존재하는지 확인
        Optional<Pill> existingPill = pillRepository.findById(pill.getDrugNum());
        if (existingPill.isPresent()) {
            return pillRepository.save(pill);  // Pill이 존재하면 업데이트
        } else {
            throw new RuntimeException("Pill not found with drugNum: " + pill.getDrugNum());  // Pill이 없으면 예외 발생
        }
    }
}
