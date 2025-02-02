package com.Master.Auction.Repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Member.MemberLikeEntity;
import com.Master.Auction.Entity.Member.MemberEntity;

public interface MemberLikeRepository extends JpaRepository<MemberLikeEntity, Long> {
    boolean existsByLikerAndTarget(MemberEntity liker, MemberEntity target);


}
