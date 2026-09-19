package dio.budgeting;

import dio.budgeting.application.output.FinancialSummaryOutput;
import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FinancialSummaryOutputTest {

    @Test
    void should_calculateIncomeExpenseAndBalance_when_transactionsAreProvided() {
        var transactions = List.of(
                new Transaction("Salário", 300_000, Category.INCOME),
                new Transaction("Mercado", 8_000, Category.GROCERIES),
                new Transaction("Remédio", 2_000, Category.PHARMA));

        var summary = FinancialSummaryOutput.from(transactions);

        assertThat(summary.totalIncome()).isEqualTo(3000.00);
        assertThat(summary.totalExpense()).isEqualTo(100.00);
        assertThat(summary.balance()).isEqualTo(2900.00);
    }
}
