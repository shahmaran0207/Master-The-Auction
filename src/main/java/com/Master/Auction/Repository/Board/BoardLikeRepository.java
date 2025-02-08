package com.Master.Auction.Repository.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Board.BoardLikeEntity;
import com.Master.Auction.Entity.Member.MemberEntity;
import com.Master.Auction.Entity.Board.BoardEntity;

public interface BoardLikeRepository extends JpaRepository<BoardLikeEntity, Long> {
    boolean existsByMemberEntityAndBoardEntity(MemberEntity member, BoardEntity board);
    int countByBoardEntity(BoardEntity board);
    void deleteByMemberEntityAndBoardEntity(MemberEntity member, BoardEntity board);
}
