package com.Master.Auction.Repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Member.MemberProfileEntity;
import com.Master.Auction.Entity.Member.MemberEntity;
import java.util.List;

public interface MemberProfileRepository extends JpaRepository<MemberProfileEntity, Long> {
    List<MemberProfileEntity> findByMemberEntity(MemberEntity exisitingMemberEntity);
}
