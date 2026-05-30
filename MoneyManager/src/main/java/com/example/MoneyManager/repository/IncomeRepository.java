package com.example.MoneyManager.repository;

import com.example.MoneyManager.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
