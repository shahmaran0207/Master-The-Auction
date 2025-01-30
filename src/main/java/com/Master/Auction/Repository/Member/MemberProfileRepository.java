package com.Master.Auction.Repository.Member;

import com.Master.Auction.Entity.Member.MemberProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfileEntity, Long> {
}
