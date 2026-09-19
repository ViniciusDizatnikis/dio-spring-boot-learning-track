package dio.budgeting.infrastructure.http.response;

import dio.budgeting.application.output.FinancialSummaryOutput;

public record FinancialSummaryResponse(double totalIncome, double totalExpense, double balance) {
    public static FinancialSummaryResponse from(FinancialSummaryOutput output) {
        return new FinancialSummaryResponse(output.totalIncome(), output.totalExpense(), output.balance());
    }
}
