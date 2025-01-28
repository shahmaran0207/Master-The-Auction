package com.Master.Auction.Repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Member.MemberProfileEntity;

public interface MemberProfileRepository extends JpaRepository<MemberProfileEntity, Long> {
}
