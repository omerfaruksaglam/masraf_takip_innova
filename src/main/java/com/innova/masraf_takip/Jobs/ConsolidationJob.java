package com.innova.masraf_takip.Jobs;

import com.innova.masraf_takip.DataAccessLayer.ConsolidatedTotalRepository;
import com.innova.masraf_takip.DataAccessLayer.TransactionRepository;
import com.innova.masraf_takip.Entities.ConsolidatedTotal;
import com.innova.masraf_takip.Entities.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConsolidationJob {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ConsolidatedTotalRepository consolidatedTotalRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void consolidateTotals() {
        LocalDate today = LocalDate.now();

        // Assuming we are consolidating for the previous day, week, and month
        LocalDate yesterday = today.minusDays(1);
        LocalDate lastWeekStart = today.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastMonthStart = today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());

        List<Long> userIds = transactionRepository.findAll()
                .stream()
                .map(Transaction::getId)
                .distinct()
                .collect(Collectors.toList());

        for (Long userId : userIds) {
            Double dailyTotal = transactionRepository.findByUserIdAndDateBetween(userId, yesterday, yesterday)
                    .stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            Double weeklyTotal = transactionRepository.findByUserIdAndDateBetween(userId, lastWeekStart, yesterday)
                    .stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            Double monthlyTotal = transactionRepository.findByUserIdAndDateBetween(userId, lastMonthStart, yesterday)
                    .stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            ConsolidatedTotal consolidatedTotal = new ConsolidatedTotal();
            consolidatedTotal.setUserId(userId);
            consolidatedTotal.setDailyTotal(dailyTotal);
            consolidatedTotal.setWeeklyTotal(weeklyTotal);
            consolidatedTotal.setMonthlyTotal(monthlyTotal);
            consolidatedTotal.setDate(today.minusDays(1));

            consolidatedTotalRepository.save(consolidatedTotal);
        }
    }
}
