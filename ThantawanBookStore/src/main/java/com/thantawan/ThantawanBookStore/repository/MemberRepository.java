package com.thantawan.ThantawanBookStore.repository;

import com.thantawan.ThantawanBookStore.model.Member;

public interface MemberRepository {

    Member findByUsername(String username);

    Member findByCitizenID(String citizenID);

    Member findByMemberID(String memberID);

    boolean existsByCitizenID(String citizenID);

    boolean existsByUsername(String username);

    boolean existByMemberID(String memberID);

    boolean existByWalletID(String walletID);

    String saveRegister(Member member);

    String editProfile(Member member);
}