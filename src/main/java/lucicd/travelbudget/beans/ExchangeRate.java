package lucicd.travelbudget.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "current_exchange_rate")
    private BigDecimal currentExchangeRate;

    @Column(name = "currency_id")
    private Integer currencyId;

    public ExchangeRate() {
    }

    public ExchangeRate(Integer id) {
        this.id = id;
    }

    public ExchangeRate(Integer id, BigDecimal currentExchangeRate) {
        this.id = id;
        this.currentExchangeRate = currentExchangeRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCurrentExchangeRate() {
        return currentExchangeRate;
    }

    public void setCurrentExchangeRate(BigDecimal currentExchangeRate) {
        this.currentExchangeRate = currentExchangeRate;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }
}
