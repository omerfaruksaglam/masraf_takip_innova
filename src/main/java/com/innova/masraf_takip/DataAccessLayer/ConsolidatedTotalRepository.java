package com.innova.masraf_takip.DataAccessLayer;

import com.innova.masraf_takip.Entities.ConsolidatedTotal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ConsolidatedTotalRepository extends JpaRepository<ConsolidatedTotal,Long> {
    List<ConsolidatedTotal> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

}
