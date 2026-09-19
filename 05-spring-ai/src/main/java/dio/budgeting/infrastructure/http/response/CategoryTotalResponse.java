package dio.budgeting.infrastructure.http.response;

import dio.budgeting.application.output.CategoryTotalOutput;

public record CategoryTotalResponse(String category, int transactionCount, double total) {
    public static CategoryTotalResponse from(CategoryTotalOutput output) {
        return new CategoryTotalResponse(output.category(), output.transactionCount(), output.total());
    }
}
