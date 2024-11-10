package com.thantawan.ThantawanBookStore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BookForm {
    private String lessorID;
    private String tagline;
    private String bName;
    private Integer pTime;
    private Integer pYear;
    private String publisher;
    private Integer rentPricePerDay;
    private Integer securityDeposit;
    private Date datebook;
    private Integer isbn;
    private BigDecimal fullPrice;
    private List<MultipartFile> images;
    private List<String> authors;
    private String category;

    // Getters and Setters
    public String getCategory() { return category; };
    public void setCategory(String category) { this.category = category;};
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

    @JsonIgnore
    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
    // Getters
    public String getLessorID() {
        return lessorID;
    }

    public String getTagline() {
        return tagline;
    }

    public String getBName() {
        return bName;
    }

    public Integer getPTime() {
        return pTime;
    }

    public Integer getPYear() {
        return pYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public Integer getRentPricePerDay() {
        return rentPricePerDay;
    }

    public Integer getSecurityDeposit() {
        return securityDeposit;
    }

    public Date getDatebook() {
        return datebook;
    }

    // Setters
    public void setLessorID(String lessorID) {
        this.lessorID = lessorID;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setBName(String bName) {
        this.bName = bName;
    }

    public void setPTime(Integer pTime) {
        this.pTime = pTime;
    }

    public void setPYear(Integer pYear) {
        this.pYear = pYear;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setRentPricePerDay(Integer rentPricePerDay) {
        this.rentPricePerDay = rentPricePerDay;
    }

    public void setSecurityDeposit(Integer securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public void setDatebook(Date datebook) {
        this.datebook = datebook;
    }
}

