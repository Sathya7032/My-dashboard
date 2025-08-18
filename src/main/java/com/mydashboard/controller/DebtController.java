package com.mydashboard.controller;

import com.mydashboard.entity.Debt;
import com.mydashboard.entity.DebtInterest;
import com.mydashboard.service.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/debt")
@RequiredArgsConstructor
public class DebtController {

    private final DebtService debtService;

    @PostMapping
    public Debt createDebt(@RequestBody Debt debt) {
        return debtService.createDebt(debt);
    }

    @PutMapping("/{id}")
    public Debt updateDebt(@PathVariable Long id, @RequestBody Debt debt) {
        return debtService.updateDebt(id, debt);
    }

    @DeleteMapping("/{id}")
    public void deleteDebt(@PathVariable Long id) {
        debtService.deleteDebt(id);
    }

    @GetMapping
    public List<Debt> getAllDebts() {
        return debtService.getAllDebts();
    }

    @GetMapping("/{id}")
    public Debt getDebtById(@PathVariable Long id) {
        return debtService.getDebtById(id);
    }

    @GetMapping("/total")
    public BigDecimal getTotalOutstanding() {
        return debtService.getTotalOutstanding();
    }

    @PostMapping("/generate-interest")
    public void generateInterest() {
        debtService.generateMonthlyInterest();
    }

    @PostMapping("/pay-interest/{interestId}")
    public DebtInterest payInterest(@PathVariable Long interestId) {
        return debtService.payInterest(interestId);
    }
}

