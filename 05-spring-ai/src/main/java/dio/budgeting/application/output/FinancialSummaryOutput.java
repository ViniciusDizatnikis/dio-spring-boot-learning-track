package dio.budgeting.application.output;

import dio.budgeting.domain.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record FinancialSummaryOutput(double totalIncome, double totalExpense, double balance) {
    public static FinancialSummaryOutput from(List<Transaction> transactions) {
        var incomeInCents = transactions.stream()
                .filter(transaction -> transaction.getCategory().isIncome())
                .mapToLong(Transaction::getAmount)
                .sum();

        var expenseInCents = transactions.stream()
                .filter(transaction -> !transaction.getCategory().isIncome())
                .mapToLong(Transaction::getAmount)
                .sum();

        return new FinancialSummaryOutput(
                toReais(incomeInCents),
                toReais(expenseInCents),
                toReais(incomeInCents - expenseInCents));
    }

    private static double toReais(long cents) {
        return BigDecimal.valueOf(cents)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
