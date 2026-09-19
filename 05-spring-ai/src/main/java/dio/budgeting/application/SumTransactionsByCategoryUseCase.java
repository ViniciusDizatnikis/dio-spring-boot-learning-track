package dio.budgeting.application;

import dio.budgeting.application.output.CategoryTotalOutput;
import dio.budgeting.domain.Category;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class SumTransactionsByCategoryUseCase {
    private final TransactionRepository transactionRepository;

    public SumTransactionsByCategoryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "sum-transactions-by-category",
            description = "Calcula o total gasto em uma categoria. O total retornado está em reais.")
    public CategoryTotalOutput execute(@ToolParam(description = "Categoria de uma transação") Category category) {
        var transactions = transactionRepository.findAllByCategory(category);

        return CategoryTotalOutput.from(category, transactions);
    }
}
