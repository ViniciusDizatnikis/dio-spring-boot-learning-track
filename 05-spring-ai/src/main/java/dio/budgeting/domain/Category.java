package dio.budgeting.domain;

public enum Category {
    GROCERIES,
    PHARMA,
    AUTO,
    INCOME;

    public boolean isIncome() {
        return this == INCOME;
    }
}
