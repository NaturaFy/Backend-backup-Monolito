package com.naturafy.macety.repository;

import com.naturafy.macety.entity.Plant;
import com.naturafy.macety.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {

    List<Plant> findByUserId(Long userId);

    @Transactional
    void deleteByUser(User user);
}
