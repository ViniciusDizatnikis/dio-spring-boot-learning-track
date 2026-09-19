package dio.budgeting.application;

import dio.budgeting.application.output.FinancialSummaryOutput;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class GetFinancialSummaryUseCase {
    private final TransactionRepository transactionRepository;

    public GetFinancialSummaryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "get-financial-summary",
            description = "Retorna o resumo financeiro do usuário: total de receitas, total de despesas e saldo. Os valores estão em reais.")
    public FinancialSummaryOutput execute() {
        return FinancialSummaryOutput.from(transactionRepository.findAll());
    }
}
