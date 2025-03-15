package com.Master.Auction.Repository.Auction;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.Auction.WinningBidEntity;
import com.Master.Auction.Entity.Auction.AuctionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface WinningBidRepository extends JpaRepository<WinningBidEntity, Long> {

    Page<WinningBidEntity> findByMember_Id(Long memberId, Pageable pageable);

    WinningBidEntity findByAuction(AuctionEntity auctionEntity);
}
