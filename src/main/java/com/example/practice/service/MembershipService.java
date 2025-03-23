package com.example.practice.service;

import com.example.practice.modal.MembershipDetails;
import com.example.practice.payload.MembershipDto;

public interface MembershipService {
    MembershipDetails getMembership(Long user_id);

    String createMembership(MembershipDto membershipDto);
}
