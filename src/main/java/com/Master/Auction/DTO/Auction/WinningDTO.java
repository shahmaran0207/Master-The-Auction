package com.Master.Auction.DTO.Auction;

import com.Master.Auction.Entity.Auction.WinningBidEntity;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WinningDTO {

    private Long id;
    private Long auctionId;
    private Long memberId;

    private String memberName;
    private String auctionRegistrar;
    private String auctionTitle;

    private int winningPrice;

    private LocalDateTime winningTime;

    public WinningDTO(Long id, Long auctionId, Long memberId, String memberName, int winningPrice, LocalDateTime winningTime, String auctionTitle, String auctionRegistrar) {
        this.id = id;
        this.auctionId = auctionId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.winningPrice = winningPrice;
        this.winningTime = winningTime;
        this.auctionTitle = auctionTitle;
        this.auctionRegistrar = auctionRegistrar;
    }

    public static WinningDTO toWinningDTO(WinningBidEntity winningBidEntity) {
        WinningDTO winningDTO = new WinningDTO();

        winningDTO.setId(winningBidEntity.getId());
        winningDTO.setWinningPrice(winningBidEntity.getWinningPrice());
        winningDTO.setWinningTime(winningBidEntity.getWinningTime());
        winningDTO.setAuctionId(winningBidEntity.getAuction().getId());
        winningDTO.setMemberId(winningBidEntity.getMember().getId());

        return winningDTO;
    }
}
