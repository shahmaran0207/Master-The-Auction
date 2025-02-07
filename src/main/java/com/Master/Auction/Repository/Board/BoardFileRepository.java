package com.Master.Auction.Repository.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Board.BoardFileEntity;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {
}
