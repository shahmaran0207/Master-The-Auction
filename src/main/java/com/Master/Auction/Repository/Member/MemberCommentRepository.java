package com.Master.Auction.Repository.Member;

import com.Master.Auction.Entity.Member.MemberCommentEntity;
import com.Master.Auction.Entity.Member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCommentRepository extends JpaRepository<MemberCommentEntity, Long> {
    List<MemberCommentEntity> findAllByCommentTargetOrderByIdDesc(MemberEntity memberEntity);
}
