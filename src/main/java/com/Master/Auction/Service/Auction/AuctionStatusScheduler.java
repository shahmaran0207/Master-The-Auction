package com.Master.Auction.Service.Auction;

import com.Master.Auction.Repository.Auction.AuctionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import com.Master.Auction.Entity.Auction.AuctionEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionStatusScheduler {

    private final AuctionRepository auctionRepository; // JPA Repository 혹은 DAO

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void updateFinishedAuctions() {
        LocalDateTime now = LocalDateTime.now();
        // endTime이 지금보다 이전이고 status가 "not finished"인 항목들을 찾기
        List<AuctionEntity> auctionsToUpdate = auctionRepository.findByEndTimeBeforeAndAuctionStatus(now, "not finished");

        if (!auctionsToUpdate.isEmpty()) {
            auctionsToUpdate.forEach(auction -> {
                auction.setAuctionStatus("finished");
            });
            auctionRepository.saveAll(auctionsToUpdate);
        }
    }
}
