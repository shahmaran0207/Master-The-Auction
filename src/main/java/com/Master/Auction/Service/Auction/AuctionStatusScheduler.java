package com.Master.Auction.Service.Auction;

import com.Master.Auction.Repository.Auction.WinningBidRepository;
import org.springframework.transaction.annotation.Transactional;
import com.Master.Auction.Repository.Auction.AuctionRepository;
import com.Master.Auction.Repository.Member.MemberRepository;
import com.Master.Auction.Repository.Auction.BidRepository;
import org.springframework.scheduling.annotation.Scheduled;
import com.Master.Auction.Entity.Auction.WinningBidEntity;
import com.Master.Auction.Entity.Auction.AuctionEntity;
import com.Master.Auction.Entity.Member.MemberEntity;
import com.Master.Auction.Entity.Auction.BidEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionStatusScheduler {

    private final AuctionRepository auctionRepository;
    private final WinningBidRepository winningBidRepository;
    private final BidRepository bidRepository;
    private final MemberRepository memberRepository;

    @Scheduled(fixedRate = 600)
    @Transactional
    public void updateFinishedAuctions() {
        LocalDateTime now = LocalDateTime.now();
        List<AuctionEntity> auctionsToUpdate = auctionRepository.findByEndTimeBeforeAndAuctionStatus(now, "not finished");

        if (!auctionsToUpdate.isEmpty()) {
            auctionsToUpdate.forEach(auction -> {
                BidEntity bid = bidRepository.findByAuctionEntity(auction);

                if (bid != null) {
                    int winningBidPrice = bid.getBidPrice();
                    MemberEntity winner = bid.getMemberEntity();

                    WinningBidEntity winningBid = new WinningBidEntity();
                    winningBid.setAuction(auction);
                    winningBid.setMember(winner);
                    winningBid.setWinningPrice(winningBidPrice);
                    winningBid.setWinningTime(LocalDateTime.now());
                    winningBidRepository.save(winningBid);

                    // 2. 경매 등록자(판매자) 정보 가져오기
                    MemberEntity seller = auction.getMemberEntity();
                    if (seller != null) {
                        seller.setMoney(seller.getMoney() + winningBidPrice);
                        memberRepository.save(seller);
                    }
                }

                // 3. 경매 상태 업데이트
                auction.setAuctionStatus("finished");
            });

            auctionRepository.saveAll(auctionsToUpdate);
        }
    }
}
