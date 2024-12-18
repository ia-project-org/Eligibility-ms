package org.eligibilityms.mapper;

import org.eligibilityms.dto.ClientDetailsDto;
import org.eligibilityms.dto.LoanDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ClientLoanMapper {

    public static LoanDto toLoanDto(ClientDetailsDto clientDetailsDto) {
        LoanDto loanDto = new LoanDto();
        loanDto.setName(clientDetailsDto.getName());
        loanDto.setEmail(clientDetailsDto.getEmail());
        loanDto.setMonth(convertMonthToString(clientDetailsDto.getMonth()));
        loanDto.setAge(clientDetailsDto.getAge());
        loanDto.setAnnual_income(clientDetailsDto.getAnnual_income());
        loanDto.setMonthly_inhand_salary(clientDetailsDto.getMonthly_inhand_salary());
        loanDto.setTotal_emi_per_month(clientDetailsDto.getTotal_emi_per_month());
        loanDto.setNum_bank_accounts(clientDetailsDto.getNum_bank_accounts());
        loanDto.setNum_credit_card(clientDetailsDto.getNum_credit_card());
        loanDto.setInterest_rate(clientDetailsDto.getInterest_rate());
        loanDto.setNum_of_loan(clientDetailsDto.getNum_of_loan());
        loanDto.setDelay_from_due_date(clientDetailsDto.getDelay_from_due_date());
        loanDto.setNum_of_delayed_payment(clientDetailsDto.getNum_of_delayed_payment());
        loanDto.setChanged_credit_limit(clientDetailsDto.getChanged_credit_limit());
        loanDto.setCredit_inquiries(clientDetailsDto.getNum_credit_inquiries());
        loanDto.setCredit_mix(clientDetailsDto.getCredit_mix());
        loanDto.setOutstanding_debt(clientDetailsDto.getOutstanding_debt());
        loanDto.setCredit_utilization_ratio(clientDetailsDto.getCredit_utilization_ratio());
        loanDto.setCredit_history_age(clientDetailsDto.getCredit_history_age());
        loanDto.setPayment_of_min_amount(clientDetailsDto.getPayment_of_min_amount().equalsIgnoreCase("Yes") ? 1 : 0);
        loanDto.setAmount_invested_monthly(clientDetailsDto.getAmount_invested_monthly());
        loanDto.setPayment_behaviour(clientDetailsDto.getPayment_behaviour());
        loanDto.setMonthly_balance(clientDetailsDto.getMonthly_balance());

        loanDto.setType_of_loan(extractLoans(clientDetailsDto));
        loanDto.setOccupation(extractOccupation(clientDetailsDto));

        return loanDto;
    }

    private static String convertMonthToString(int month) {
        return switch (month) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "Unknown";
        };
    }

    private static void mapLoans(ClientDetailsDto clientDetailsDto, List<String> loans) {
        if (loans != null) {
            clientDetailsDto.setAuto_loan(loans.contains("Auto Loan") ? 1 : 0);
            clientDetailsDto.setCredit_builder_loan(loans.contains("Credit-Builder Loan") ? 1 : 0);
            clientDetailsDto.setPersonal_loan(loans.contains("Personal Loan") ? 1 : 0);
        }
    }

    private static List<String> extractLoans(ClientDetailsDto clientDetailsDto) {
        return Stream.of(
                clientDetailsDto.getAuto_loan() == 1 ? "Auto Loan" : null,
                clientDetailsDto.getCredit_builder_loan() == 1 ? "Credit-Builder Loan" : null,
                clientDetailsDto.getPersonal_loan() == 1 ? "Personal Loan" : null
        ).filter(Objects::nonNull).toList();
    }

    private static void mapOccupation(ClientDetailsDto clientDetailsDto, String occupation) {
        if (occupation != null) {
            clientDetailsDto.setOccupation_Scientist(occupation.equals("Scientist") ? 1 : 0);
            clientDetailsDto.setOccupation_Doctor(occupation.equals("Doctor") ? 1 : 0);
            clientDetailsDto.setOccupation_Engineer(occupation.equals("Engineer") ? 1 : 0);
            clientDetailsDto.setOccupation_Lawyer(occupation.equals("Lawyer") ? 1 : 0);
            clientDetailsDto.setOccupation_Teacher(occupation.equals("Teacher") ? 1 : 0);
        }
    }

    private static String extractOccupation(ClientDetailsDto clientDetailsDto) {
        if (clientDetailsDto.getOccupation_Scientist() == 1) return "Scientist";
        if (clientDetailsDto.getOccupation_Doctor() == 1) return "Doctor";
        if (clientDetailsDto.getOccupation_Engineer() == 1) return "Engineer";
        if (clientDetailsDto.getOccupation_Lawyer() == 1) return "Lawyer";
        if (clientDetailsDto.getOccupation_Teacher() == 1) return "Teacher";
        return "Unknown";
    }
}
