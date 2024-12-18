package org.eligibilityms.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDetailsDto {
    private Long clientId;
    private String name;
    private String email;
    private int month;
    private int age;
    private double annual_income;
    private double monthly_inhand_salary;
    private double total_emi_per_month;
    private int num_bank_accounts;
    private int num_credit_card;
    private double interest_rate;
    private int num_of_loan;
    private int delay_from_due_date;
    private int num_of_delayed_payment;
    private int changed_credit_limit;
    private int num_credit_inquiries;
    private String credit_mix;
    private double outstanding_debt;
    private double credit_utilization_ratio;
    private int credit_history_age;
    private String payment_of_min_amount;
    private double amount_invested_monthly;
    private int payment_behaviour;
    private double monthly_balance;

    // Loans
    private int auto_loan;
    private int credit_builder_loan;
    private int debt_consolidation_loan;
    private int home_equity_loan;
    private int mortgage_loan;
    private int no_loan;
    private int not_specified;
    private int payday_loan;
    private int personal_loan;
    private int student_loan;

    // Occupation
    private int occupation_Accountant;
    private int occupation_Architect;
    private int occupation_Developer;
    private int occupation_Doctor;
    private int occupation_Engineer;
    private int occupation_Entrepreneur;
    private int occupation_Journalist;
    private int occupation_Lawyer;
    private int occupation_Manager;
    private int occupation_Mechanic;
    private int occupation_Media_Manager;
    private int occupation_Musician;
    private int occupation_Scientist;
    private int occupation_Teacher;
    private int occupation_Writer;
}
