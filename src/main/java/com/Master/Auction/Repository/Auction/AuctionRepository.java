package com.Master.Auction.Repository.Auction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import com.Master.Auction.Entity.Auction.AuctionEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<AuctionEntity, Long> {
    List<AuctionEntity> findByEndTimeBeforeAndAuctionStatus(LocalDateTime now, String auctionStatus);

    @Modifying(clearAutomatically = true)
    @Query("update AuctionEntity a set a.AuctionHits = a.AuctionHits + 1 where a.id = :id")
    void updateHits(@Param("id") Long id);

    Page<AuctionEntity> findByAuctionStatus(String auctionStatus, Pageable pageable);


}
