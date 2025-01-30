package com.Master.Auction.Repository.Member;

import com.Master.Auction.Entity.Member.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    Optional<Email> findByEmail(String email);
}
