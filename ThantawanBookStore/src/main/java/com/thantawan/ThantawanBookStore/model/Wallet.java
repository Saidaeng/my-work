package com.thantawan.ThantawanBookStore.model;

import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "Wallet")
public class Wallet {

    @Id
    @Column(name = "WalletID", nullable = false)
    private String walletID;

    @Id
    @ManyToOne
    @JoinColumn(name = "MemberID", nullable = false)
    private Member member;

    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;


    // For Wallet class
    public String getWalletID() {
        return walletID;
    }

    public void setWalletID(String walletID) {
        this.walletID = walletID;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
