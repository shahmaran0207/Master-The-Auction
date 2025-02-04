package com.Master.Auction.Repository.Auction;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Auction.AuctionEntity;

public interface AuctionRepository extends JpaRepository<AuctionEntity, Long> {
}
