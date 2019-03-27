package lucicd.travelbudget.forms;

public class ExchangeRateForm {
    private String id;
    private String currentExchangeRate;
    private String currencyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentExchangeRate() {
        return currentExchangeRate;
    }

    public void setCurrentExchangeRate(String currentExchangeRate) {
        this.currentExchangeRate = currentExchangeRate;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }
}