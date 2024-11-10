package com.thantawan.ThantawanBookStore.controllers;

import com.thantawan.ThantawanBookStore.WebAlgorithm.GenerateRandomID;
import com.thantawan.ThantawanBookStore.WebAlgorithm.PasswordHash;
import com.thantawan.ThantawanBookStore.model.Member;
import com.thantawan.ThantawanBookStore.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemberControllers {

    @Autowired
    private MemberRepository memberRepository;

    private String generateUniqueMemberID() {
        String generatedMemberId;
        do {
            generatedMemberId = GenerateRandomID.generateRandomNumberString(10);
        } while (memberRepository.existByMemberID(generatedMemberId));
        return generatedMemberId;
    }

    @PostMapping("/getData")
    public Member getMemberDataByID(@RequestBody String memberID){
        memberID = memberID.split(":")[1].replaceAll("[^0-9]", "");
        return memberRepository.findByMemberID(memberID);
    }



    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Member member) {
        Map<String, Object> response = new HashMap<>();
        Member memberUsernameInDb = memberRepository.findByUsername(member.getUsername());
        Member memberCitizenIDInDb = memberRepository.findByCitizenID(member.getCitizenID());

        if (memberUsernameInDb != null) {
            try {
                if (PasswordHash.checkPassword(member.getPassword(),memberUsernameInDb.getPassword())) {
                    response.put("success", true);
                    response.put("member", memberUsernameInDb);
                    response.put("memberID", memberUsernameInDb.getMemberID());
                } else {
                    response.put("success", false);
                    response.put("message", "Invalid username/citizenID or password");
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
                response.put("success", false);
                response.put("message", "Error occurred while checking the password");
            }
        } else if (memberCitizenIDInDb != null) {
            try {
                if (PasswordHash.checkPassword(member.getPassword(),memberCitizenIDInDb.getPassword())) {
                    response.put("success", true);
                    response.put("member", memberCitizenIDInDb);
                    response.put("memberID", memberCitizenIDInDb.getMemberID());
                } else {
                    response.put("success", false);
                    response.put("message", "Invalid username/citizenID or password");
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
                response.put("success", false);
                response.put("message", "Error occurred while checking the password");
            }
        } else {
            response.put("success", false);
            response.put("message", "User not found");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/editProfile")
    public ResponseEntity<Map<String, Object>> editProfile(@RequestBody Member member) {
        Map<String, Object> response = new HashMap<>();

        String result = memberRepository.editProfile(member);

        if ("Successfully edit your profile".equals(result)) {
            response.put("success", true);
            response.put("member", member);
        } else {
            response.put("success", false);
            response.put("message", result);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Member member) {
        Map<String, Object> response = new HashMap<>();

        member.setMemberID(generateUniqueMemberID());
        try {
            member.setPassword(PasswordHash.generateStrongPasswordHash(member.getPassword()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error occurred while hashing the password");
            return ResponseEntity.ok(response);
        }

        String result = memberRepository.saveRegister(member);

        if ("Registration successful".equals(result)) {
            response.put("success", true);
            response.put("member", member);
        } else {
            response.put("success", false);
            response.put("message", result);
        }

        return ResponseEntity.ok(response);
    }

    // Add other CRUD operations as needed
}