package org.eligibilityms.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoanDto {
        private String name;
        private String email;
        private String month;
        private int age;
        private String occupation;
        private double annual_income;
        private double monthly_inhand_salary;
        private double total_emi_per_month;
        private int num_bank_accounts;
        private int num_credit_card;
        private double interest_rate;
        private int num_of_loan;
        private double delay_from_due_date;
        private double num_of_delayed_payment;
        private double changed_credit_limit;
        private int credit_inquiries;
        private String credit_mix;
        private double outstanding_debt;
        private double credit_utilization_ratio;
        private int credit_history_age;
        private int payment_of_min_amount;
        private double amount_invested_monthly;
        private int payment_behaviour;
        private double monthly_balance;
        private int credit_score;
        private List<String> type_of_loan;
}
