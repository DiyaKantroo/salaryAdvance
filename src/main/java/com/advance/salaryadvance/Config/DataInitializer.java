package com.advance.salaryadvance.Config;

import com.advance.salaryadvance.Entity.LoanCriterion;
import com.advance.salaryadvance.Repository.LoanCriterionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final LoanCriterionRepository loanCriterionRepository;

    public DataInitializer(LoanCriterionRepository loanCriterionRepository) {
        this.loanCriterionRepository = loanCriterionRepository;
    }

    @Override
    public void run(String... args) {
        if (loanCriterionRepository.count() == 0) {
            LoanCriterion criterion = new LoanCriterion();
            criterion.setMinSalary(30000.0);
            criterion.setMaxLoanAmount(50000.0);
            criterion.setInterestRate(12.5);
            criterion.setMaxTenure(12);
            loanCriterionRepository.save(criterion);
            System.out.println("Default LoanCriterion inserted.");
        }
    }
} 