package dio.budgeting.application.input;

import dio.budgeting.domain.Category;
import org.springframework.ai.tool.annotation.ToolParam;

public record PersistTransactionInput(@ToolParam(description = "Descrição da transação") String description,
                                      @ToolParam(description = "Valor da transação (em centavos, sempre positivo)") long amount,
                                      @ToolParam(description = "Categoria de uma transação") Category category) {
}
