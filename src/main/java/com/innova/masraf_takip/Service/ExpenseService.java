package com.innova.masraf_takip.Service;

import com.innova.masraf_takip.DataAccessLayer.ConsolidatedTotalRepository;
import com.innova.masraf_takip.DataAccessLayer.TransactionRepository;
import com.innova.masraf_takip.Entities.ConsolidatedTotal;
import com.innova.masraf_takip.Entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ConsolidatedTotalRepository consolidatedTotalRepository;

    public Double getTotalAmount(Long userId, LocalDate startDate, LocalDate endDate) {
        List<ConsolidatedTotal> consolidatedTotals = consolidatedTotalRepository.findByUserIdAndDateBetween(userId, startDate, endDate);

        Double consolidatedSum = consolidatedTotals.stream()
                .mapToDouble(ct -> {
                    if (ct.getDate().isAfter(startDate.minusDays(1)) && ct.getDate().isBefore(endDate.plusDays(1))) {
                        return ct.getDailyTotal();
                    } else if (ct.getDate().getDayOfMonth() == 1 && ct.getDate().getMonth() == startDate.getMonth()) {
                        return ct.getMonthlyTotal();
                    } else if (ct.getDate().getDayOfWeek().getValue() == 1 && ct.getDate().getMonth() == startDate.getMonth()) {
                        return ct.getWeeklyTotal();
                    } else {
                        return 0.0;
                    }
                }).sum();

        List<Transaction> liveTransactions = transactionRepository.findByUserIdAndDateBetween(userId, startDate, endDate);

        Double liveSum = liveTransactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();

        return consolidatedSum + liveSum;
    }
}