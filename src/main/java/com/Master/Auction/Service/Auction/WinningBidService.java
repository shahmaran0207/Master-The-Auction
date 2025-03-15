package com.Master.Auction.Service.Auction;

import com.Master.Auction.Repository.Auction.WinningBidRepository;
import com.Master.Auction.Repository.Auction.AuctionRepository;
import com.Master.Auction.Entity.Auction.WinningBidEntity;
import com.Master.Auction.Entity.Auction.AuctionEntity;
import org.springframework.data.domain.PageRequest;
import com.Master.Auction.DTO.Auction.WinningDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WinningBidService {
    private final WinningBidRepository winningBidRepository;
    private final AuctionRepository auctionRepository;

    public Page<WinningDTO> WinningBidList(Pageable pageable, Long id) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<WinningBidEntity> winningBidEntities;

        winningBidEntities = winningBidRepository.findByMember_Id(id,
                PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"))
        );

        return winningBidEntities.map(winningBid ->
                new WinningDTO(
                        winningBid.getId(), winningBid.getAuction().getId(), winningBid.getMember().getId(),
                        winningBid.getMember().getMemberName(),
                        winningBid.getWinningPrice(), winningBid.getWinningTime(), winningBid.getAuction().getAuctionTitle(),
                        winningBid.getAuction().getMemberEntity().getMemberName()
                ));
    }

    public WinningDTO findByAuctionId(Long AuctionId) {
        AuctionEntity auctionEntity = auctionRepository.findById(AuctionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid auction id: " + AuctionId));

        WinningBidEntity winningBidEntity = winningBidRepository.findByAuction(auctionEntity);

        if (winningBidEntity==null) return null;
        else{
            return WinningDTO.toWinningDTO(winningBidEntity);
        }
    }
}
