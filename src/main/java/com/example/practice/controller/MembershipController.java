package com.example.practice.controller;

import com.example.practice.modal.MembershipDetails;
import com.example.practice.payload.MembershipDto;
import com.example.practice.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("membership")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @GetMapping("/{user_id}")
    public ResponseEntity<MembershipDetails> getMemberShip(@PathVariable("user_id") Long user_id) {
        return ResponseEntity.ok(membershipService.getMembership(user_id));
    }

    @PostMapping("/")
    public ResponseEntity<String> createMembership(@RequestBody MembershipDto membershipDto) {
        return new ResponseEntity<>(membershipService.createMembership(membershipDto), HttpStatus.CREATED);
    }
}
