package com.Master.Auction.Repository.Auction;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Auction.AuctionEntity;
import com.Master.Auction.Entity.Auction.BidEntity;

public interface BidRepository extends JpaRepository<BidEntity, Long> {
    BidEntity findByAuctionEntity(AuctionEntity auctionEntity);
}
