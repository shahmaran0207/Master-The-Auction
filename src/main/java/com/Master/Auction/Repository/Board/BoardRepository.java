package com.Master.Auction.Repository.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import com.Master.Auction.Entity.Board.BoardEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update BoardEntity b set b.boardHits = b.boardHits + 1 where b.id = :id")
    void updateHits(@Param("id") Long id);

    Page<BoardEntity> findByMemberEntity_Id(Long memberId, Pageable pageable);
}