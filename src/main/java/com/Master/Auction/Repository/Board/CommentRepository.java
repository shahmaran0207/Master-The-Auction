package com.Master.Auction.Repository.Board;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Board.CommentEntity;
import com.Master.Auction.Entity.Board.BoardEntity;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);

    @Transactional
    void deleteByBoardEntity_Id(Long boardId);
}