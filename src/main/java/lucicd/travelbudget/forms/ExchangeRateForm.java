package lucicd.travelbudget.forms;

import java.math.BigDecimal;
import lucicd.travelbudget.beans.ExchangeRate;

public class ExchangeRateForm {
    private String id;
    private String currentExchangeRate;
    private String currencyId;
    private String currencyName;
    
    public ExchangeRateForm() {}

    public ExchangeRateForm(ExchangeRate rec) {
        this.id = rec.getId().toString();

        BigDecimal rate = rec.getCurrentExchangeRate();
        if (rate != null) {
            this.currentExchangeRate = rate.toString();
        }

        Integer myId = rec.getCurrencyId();
        if (myId != null) {
            this.currencyId = myId.toString();
        }
    }
    
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

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}