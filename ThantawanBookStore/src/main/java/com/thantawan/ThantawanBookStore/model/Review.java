package com.thantawan.ThantawanBookStore.model;
import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "Review")
public class Review {

    @Id
    @ManyToOne
    @JoinColumn(name = "TenantID", referencedColumnName = "MemberID", nullable = false)
    private Member tenant;

    @Id
    @ManyToOne
    @JoinColumn(name = "BookID", referencedColumnName = "BookID", nullable = false)
    private Book book;

    @Column(name = "Rating", nullable = false)
    private Integer rating;

    public Member getTenant() {
        return tenant;
    }

    public void setTenant(Member tenant) {
        this.tenant = tenant;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
