package lucicd.travelbudget.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "budget_plan_items")
public class BudgetPlanItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "item_description")
    private String itemDescription;
    
    @Column(name = "url")
    private String url;
    
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Column(name = "completion_date")
    @Temporal(TemporalType.DATE)
    private Date completionDate;

    @Column(name = "cost_in_currency")
    private BigDecimal costInCurrency;

    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    @Column(name = "status")
    private String status;

    @Column(name = "comments")
    private String comments;

    @Column(name = "budget_plan_id")
    private Integer budgetPlanId;
    
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "currency_id")
    private Integer currencyId;

    public BudgetPlanItem() {
    }

    public BudgetPlanItem(Integer id) {
        this.id = id;
    }

    public BudgetPlanItem(Integer id, String itemDescription, Date startDate, 
            Date completionDate, BigDecimal costInCurrency, 
            BigDecimal exchangeRate, String status)
    {
        this.id = id;
        this.itemDescription = itemDescription;
        this.startDate = startDate;
        this.completionDate = completionDate;
        this.costInCurrency = costInCurrency;
        this.exchangeRate = exchangeRate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public BigDecimal getCostInCurrency() {
        return costInCurrency;
    }

    public void setCostInCurrency(BigDecimal costInCurrency) {
        this.costInCurrency = costInCurrency;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getBudgetPlanId() {
        return budgetPlanId;
    }

    public void setBudgetPlanId(Integer budgetPlanId) {
        this.budgetPlanId = budgetPlanId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }
}
