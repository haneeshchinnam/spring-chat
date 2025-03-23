package com.example.practice.repository;

import com.example.practice.modal.MembershipDetails;
import com.example.practice.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<MembershipDetails, Long> {

    @Query(value = "SELECT * FROM membership_details u WHERE u.user_id = :id", nativeQuery = true)
    Optional<MembershipDetails> findMembershipUserById(@Param("id") Long id);
}
