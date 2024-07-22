package com.innova.masraf_takip.DataAccessLayer;

import com.innova.masraf_takip.Entities.ConsolidatedTotal;

import java.time.LocalDate;
import java.util.List;

public interface ConsolidatedTotalRepository {
    List<ConsolidatedTotal> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

}
