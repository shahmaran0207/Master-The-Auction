package com.Master.Auction.Repository.Board;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Board.BoardHateEntity;
import com.Master.Auction.Entity.Board.BoardEntity;

public interface BoardHateRepository extends JpaRepository<BoardHateEntity, Long> {
    boolean existsByMemberEntityAndBoardEntity(com.Master.Auction.Entity.Member.MemberEntity member, BoardEntity board);

    int countByBoardEntity(BoardEntity board);

    void deleteByMemberEntityAndBoardEntity(com.Master.Auction.Entity.Member.MemberEntity member, BoardEntity board);

    @Transactional
    void deleteByBoardEntity_Id(Long boardId);
}
