package com.Master.Auction.Repository.Board;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Board.BoardFileEntity;
import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {
    List<BoardFileEntity> findByBoardEntity_Id(Long id);

    @Transactional
    void deleteByBoardEntity_Id(Long id);
}
