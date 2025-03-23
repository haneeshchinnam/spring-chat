package com.example.practice.serviceImpl;

import com.example.practice.exception.UserNotFound;
import com.example.practice.modal.Book;
import com.example.practice.modal.MembershipDetails;
import com.example.practice.modal.User;
import com.example.practice.payload.MembershipDto;
import com.example.practice.repository.BookRepository;
import com.example.practice.repository.MembershipRepository;
import com.example.practice.repository.UserRepository;
import com.example.practice.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MembershipDetails getMembership(Long user_id) {
        Optional<MembershipDetails> membershipDetails = membershipRepository.findMembershipUserById(user_id);
        if(membershipDetails.isPresent()) {
            return membershipDetails.get();
        } else {
            throw new UserNotFound("User does not have a membership");
        }
    }

    @Override
    public String createMembership(MembershipDto membershipDto) {
        try {
            membershipRepository.save(membershipDtoToDetails(membershipDto));
            return "Membership created";
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            throw new UserNotFound(e.getMessage());
        }
    }

    public MembershipDetails membershipDtoToDetails(MembershipDto membershipDto) {
        System.out.println("user_id "+membershipDto.getUserId()+" "+membershipDto.getMembershipType());
        User user = userRepository.findUserById(membershipDto.getUserId())
                .orElseThrow(() -> new UserNotFound(String.format("User Id %d user does not exist", membershipDto.getUserId())));
        MembershipDetails membershipDetails = new MembershipDetails();
        membershipDetails.setMembershipType(membershipDto.getMembershipType());
        membershipDetails.setExpirationDate(LocalDate.parse(membershipDto.getExpirationDate()));
        membershipDetails.setUser(user);
        System.out.println("Membership details "+ membershipDetails.getMembershipType()+" "+ membershipDetails.getUser().getId());
        return membershipDetails;
    }
}
