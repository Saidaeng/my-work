package com.thantawan.ThantawanBookStore.repository;

import com.thantawan.ThantawanBookStore.WebAlgorithm.GenerateRandomID;
import com.thantawan.ThantawanBookStore.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMemberRepository implements MemberRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Member findByUsername(String username) {
        String sql = "SELECT * FROM Member WHERE username = ?";
        RowMapper<Member> rowMapper = (rs, rowNum) -> {
            Member member = new Member();
            member.setUsername(rs.getString("username"));
            member.setPassword(rs.getString("password"));
            member.setMemberID(rs.getString("memberID"));
            return member;
        };
        List<Member> members = jdbcTemplate.query(sql, rowMapper, username);
        return members.isEmpty() ? null : members.get(0);
    }

    @Override
    public Member findByCitizenID(String citizenID) {
        String sql = "SELECT * FROM Member WHERE CitizenID = ?";
        RowMapper<Member> rowMapper = (rs, rowNum) -> {
            Member member = new Member();
            member.setCitizenID(rs.getString("citizenID"));
            member.setPassword(rs.getString("password"));
            member.setMemberID(rs.getString("memberID"));
            return member;
        };
        List<Member> members = jdbcTemplate.query(sql, rowMapper, citizenID);
        return members.isEmpty() ? null : members.get(0);
    }

    @Override
    public Member findByMemberID(String memberID) {
        String sql = "SELECT * FROM Member WHERE MemberID = ?";
        RowMapper<Member> rowMapper = (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberID(rs.getString("MemberID"));
            member.setFname(rs.getString("Fname"));
            member.setLname(rs.getString("Lname"));
            member.setCitizenID(rs.getString("CitizenID"));
            member.setEmail(rs.getString("Email"));
            member.setUsername(rs.getString("Username"));
            member.setPassword(rs.getString("Password"));
            member.setTel(rs.getString("Tel"));
            member.setAbout(rs.getString("About"));
            member.setStreet(rs.getString("Street"));
            member.setDistrict(rs.getString("District"));
            member.setProvince(rs.getString("Province"));
            member.setZipcode(rs.getInt("Zipcode"));
            return member;
        };
        List<Member> members = jdbcTemplate.query(sql, rowMapper, memberID);
        Member result = members.isEmpty() ? null : members.get(0);
        return result;
    }

    @Override
    public boolean existsByCitizenID(String citizenID) {
        String sql = "SELECT COUNT(*) FROM Member WHERE CitizenID = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[] { citizenID }, Integer.class);

        return count != null && count > 0;
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM Member WHERE Username = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[] { username }, Integer.class);

        return count != null && count > 0;
    }

    @Override
    public boolean existByMemberID(String memberID) {
        String sql = "SELECT COUNT(*) FROM Member WHERE MemberID = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[] { memberID }, Integer.class);

        return count != null && count > 0;
    }

    @Override
    public boolean existByWalletID(String walletID) {
        String sql = "SELECT COUNT(*) FROM Wallet WHERE WalletID = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[] { walletID }, Integer.class);

        return count != null && count > 0;
    }

    @Override
    public String saveRegister(Member member) {
        String sqlMember = "INSERT INTO Member (MemberID, Username, Password, Email, CitizenID) VALUES (?, ?, ?, ?, ?)";
        String sqlWallet = "INSERT INTO Wallet (WalletID, MemberID, Balance) VALUES (?, ?, ?)";

        if (!existsByUsername(member.getUsername()) && !existsByCitizenID(member.getCitizenID())) {
            jdbcTemplate.update(sqlMember, member.getMemberID(), member.getUsername(), member.getPassword(),
                    member.getEmail(), member.getCitizenID());

            String generatedWalletId;
            do {
                generatedWalletId = GenerateRandomID.generateRandomNumberString(10);
            } while (existByWalletID(generatedWalletId));

            jdbcTemplate.update(sqlWallet, generatedWalletId, member.getMemberID(), 0.00);

            return "Registration successful";
        } else if (existsByUsername(member.getUsername())) {
            return "The username you have entered is already in use. \nPlease enter a different username.";
        } else if (existsByCitizenID(member.getCitizenID())) {
            return "The CitizenID you have entered is already in use. \nPlease enter a different CitizenID.";
        } else {
            return "Error occurred while registering the member";
        }
    }

    @Override
    public String editProfile(Member member) {
        String sql = "UPDATE Member SET Fname = ?, Lname = ?, Email = ?, About = ?, Street = ?, District = ?, Province = ?, Zipcode = ?, Tel = ? WHERE MemberID = ?";
        if (existByMemberID(member.getMemberID())) {
            jdbcTemplate.update(sql, member.getFname(), member.getLname(), member.getEmail(), member.getAbout(),
                    member.getStreet(), member.getDistrict(), member.getProvince(), member.getZipcode(),
                    member.getTel(), member.getMemberID());
            return "Successfully edit your profile";
        } else {
            return "Error occurred while edit profile";
        }
    }

}
