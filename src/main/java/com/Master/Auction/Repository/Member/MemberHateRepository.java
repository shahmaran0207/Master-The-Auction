package com.Master.Auction.Repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Member.MemberHateEntity;

public interface MemberHateRepository extends JpaRepository<MemberHateEntity, Long> {
}
