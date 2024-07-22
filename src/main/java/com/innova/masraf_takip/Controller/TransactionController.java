package com.innova.masraf_takip.Controller;

import com.innova.masraf_takip.Entities.Transaction;
import com.innova.masraf_takip.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/innova/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }

    @GetMapping("/total/{personId}")
    public Double getTotalExpenses(@PathVariable Long personId) {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return transactions.stream()
                .filter(transaction -> transaction.getUser().getId().equals(personId))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

}
