/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucicd.travelbudget.beans;

import lucicd.travelbudget.beans.BudgetPlan;
import lucicd.travelbudget.beans.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Drazen
 */
@Entity
@Table(name = "budget_plan_items")
public class BudgetPlanItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "item_description")
    private String itemDescription;
    @Size(max = 1024)
    @Column(name = "url")
    private String url;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "completion_date")
    @Temporal(TemporalType.DATE)
    private Date completionDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "cost_in_currency")
    private BigDecimal costInCurrency;
    @Basic(optional = false)
    @NotNull
    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "status")
    private String status;
    @Size(max = 1024)
    @Column(name = "comments")
    private String comments;
    @JoinColumn(name = "budget_plan_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BudgetPlan budgetPlanId;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Category categoryId;
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Currency currencyId;

    public BudgetPlanItem() {
    }

    public BudgetPlanItem(Integer id) {
        this.id = id;
    }

    public BudgetPlanItem(Integer id, String itemDescription, Date startDate, Date completionDate, BigDecimal costInCurrency, BigDecimal exchangeRate, String status) {
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

    public BudgetPlan getBudgetPlanId() {
        return budgetPlanId;
    }

    public void setBudgetPlanId(BudgetPlan budgetPlanId) {
        this.budgetPlanId = budgetPlanId;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BudgetPlanItem)) {
            return false;
        }
        BudgetPlanItem other = (BudgetPlanItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lucicd.travelbudget.model.BudgetPlanItem[ id=" + id + " ]";
    }
    
}
