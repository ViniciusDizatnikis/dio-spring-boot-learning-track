package dio.budgeting.application.output;

import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record CategoryTotalOutput(String category, int transactionCount, double total) {
    public static CategoryTotalOutput from(Category category, List<Transaction> transactions) {
        var totalInCents = transactions.stream().mapToLong(Transaction::getAmount).sum();

        return new CategoryTotalOutput(
                category.name(),
                transactions.size(),
                BigDecimal.valueOf(totalInCents)
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                        .doubleValue());
    }
}
