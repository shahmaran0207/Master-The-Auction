package com.Master.Auction.Repository.Auction;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Auction.AuctionFileEntity;

public interface AuctionFileRepository extends JpaRepository<AuctionFileEntity, Long> {
}
