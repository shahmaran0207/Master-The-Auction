package com.Master.Auction.Repository.Auction;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Auction.AuctionEntity;
import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<AuctionEntity, Long> {
    List<AuctionEntity> findByEndTimeBeforeAndAuctionStatus(LocalDateTime now, String auctionStatus);
}
