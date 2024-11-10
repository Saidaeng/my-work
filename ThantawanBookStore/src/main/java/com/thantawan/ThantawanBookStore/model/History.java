package com.thantawan.ThantawanBookStore.model;
import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Entity
@Table(name = "History")
public class History {

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "TenantID", referencedColumnName = "TenantID"),
            @JoinColumn(name = "BookID", referencedColumnName = "BookID")
    })
    private Book book;

    @Column(name = "RentDate", nullable = false)
    private Date rentDate;

    @Column(name = "DueDate", nullable = false)
    private Date dueDate;

    @Column(name = "TotalRentDay", nullable = false)
    private Integer totalRentDay;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getTotalRentDay() {
        return totalRentDay;
    }

    public void setTotalRentDay(Integer totalRentDay) {
        this.totalRentDay = totalRentDay;
    }
}
