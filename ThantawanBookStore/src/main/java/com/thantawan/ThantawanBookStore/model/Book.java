package com.thantawan.ThantawanBookStore.model;

import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "BookID", nullable = false)
    private String bookID;

    @Id
    @ManyToOne
    @JoinColumn(name = "LessorID", nullable = false)
    private Member lessor;

    @Column(name = "Tagline")
    private String tagline;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @Column(name = "B_name", nullable = false)
    private String bName;

    @Column(name = "ISBN")
    private Integer isbn;

    @Column(name = "Full_price", precision = 10, scale = 2)
    private BigDecimal fullPrice;

    @Column(name = "P_time")
    private Integer pTime;

    @Column(name = "P_year")
    private Integer pYear;

    @Column(name = "Publisher")
    private String publisher;

    @Column(name = "Datebook", nullable = false)
    private Date datebook;

    @Column(name = "Rent_Price_Per_Day", nullable = false)
    private Integer rentPricePerDay;

    @Column(name = "Security_Deposit", nullable = false)
    private Integer securityDeposit;

    @Column(name = "Cost")
    private Integer cost;

    @Column(name = "Status_lo")
    private String statusLo;

    @Column(name = "Logic_com")
    private String logicCom;

    @Column(name = "Start_date")
    private Date startDate;

    @Column(name = "End_date")
    private Date endDate;

    @Column(name = "TenantID")
    private String tenantID;

    @ManyToOne
    @JoinColumn(name = "CID", nullable = false)
    private Category category;

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public Member getLessor() {
        return lessor;
    }

    public void setLessor(Member lessor) {
        this.lessor = lessor;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getBName() {
        return bName;
    }

    public void setBName(String bName) {
        this.bName = bName;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    public Integer getPTime() {
        return pTime;
    }

    public void setPTime(Integer pTime) {
        this.pTime = pTime;
    }

    public Integer getPYear() {
        return pYear;
    }

    public void setPYear(Integer pYear) {
        this.pYear = pYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getDatebook() {
        return datebook;
    }

    public void setDatebook(Date datebook) {
        this.datebook = datebook;
    }

    public Integer getRentPricePerDay() {
        return rentPricePerDay;
    }

    public void setRentPricePerDay(Integer rentPricePerDay) {
        this.rentPricePerDay = rentPricePerDay;
    }

    public Integer getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(Integer securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getStatusLo() {
        return statusLo;
    }

    public void setStatusLo(String statusLo) {
        this.statusLo = statusLo;
    }

    public String getLogicCom() {
        return logicCom;
    }

    public void setLogicCom(String logicCom) {
        this.logicCom = logicCom;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenant(String tenantID) {
        this.tenantID = tenantID;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
