/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mahbuburrahman
 */
@Entity
@Table(name = "tax")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tax.findAll", query = "SELECT t FROM Tax t")
    , @NamedQuery(name = "Tax.findById", query = "SELECT t FROM Tax t WHERE t.id = :id")
    , @NamedQuery(name = "Tax.findByAmount", query = "SELECT t FROM Tax t WHERE t.amount = :amount")
    , @NamedQuery(name = "Tax.findByTaxRate", query = "SELECT t FROM Tax t WHERE t.taxRate = :taxRate")
    , @NamedQuery(name = "Tax.findByTotalAmount", query = "SELECT t FROM Tax t WHERE t.totalAmount = :totalAmount")
    , @NamedQuery(name = "Tax.findByCurrency", query = "SELECT t FROM Tax t WHERE t.currency = :currency")
    , @NamedQuery(name = "Tax.findByCreatedDate", query = "SELECT t FROM Tax t WHERE t.createdDate = :createdDate")})
public class Tax implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "amount")
    private BigDecimal amount;
    @Basic(optional = false)
    @Column(name = "tax_rate")
    private BigDecimal taxRate;
    @Basic(optional = false)
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Basic(optional = false)
    @Column(name = "currency")
    private String currency;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Column(name = "mdb_call_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mdbCallDate;

    public Tax() {
    }

    public Tax(Integer id) {
        this.id = id;
    }

    public Tax(double amount, double taxRate, String currency, Date mdbCallDate) {
        this.amount = BigDecimal.valueOf(amount);
        this.taxRate = BigDecimal.valueOf(taxRate);
        this.currency = currency;
        this.mdbCallDate = mdbCallDate;
    }
    
    public Tax(Integer id, BigDecimal amount, BigDecimal taxRate, BigDecimal totalAmount, String currency, Date createdDate) {
        this.id = id;
        this.amount = amount;
        this.taxRate = taxRate;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    
    public double getAmountAsDouble() {
        return amount.doubleValue();
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }
    
    public Double getTaxRateAsDouble() {
        return taxRate.doubleValue();
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public Double getTotalAmountAsDouble() {
        return totalAmount.doubleValue();
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getMdbCallDate() {
        return mdbCallDate;
    }

    public void setMdbCallDate(Date mdbCallDate) {
        this.mdbCallDate = mdbCallDate;
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
        if (!(object instanceof Tax)) {
            return false;
        }
        Tax other = (Tax) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.hsfulda.ai.gsd.middleware.Tax[ id=" + id + " ]";
    }

    @PrePersist
    public void beforeInsert () {
        this.setCreatedDate(new Date());
    }
    
    public static final DecimalFormat df = new DecimalFormat("#0.00");
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    
    public String toStringAsCommaSeperated () {
        
        StringBuffer sb = new StringBuffer();
        
//        sb.append(sdf.format(this.getCreatedDate()));
//        sb.append(",");
        sb.append(sdf.format(this.getMdbCallDate()));
        sb.append(",");
        sb.append(df.format(this.getAmountAsDouble()));
        sb.append(",");
        sb.append(df.format(this.getTaxRateAsDouble()));
        sb.append(",");
        sb.append(df.format(this.getTotalAmountAsDouble()));
        sb.append(",");
        sb.append(this.getCurrency());
        
        return sb.toString();
    }
}
