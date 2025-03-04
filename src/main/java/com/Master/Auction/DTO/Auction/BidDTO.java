package com.Master.Auction.DTO.Auction;

import com.Master.Auction.Entity.Auction.BidEntity;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BidDTO {
    private Long id;
    private Long auctionId;
    private Long memberId;
    private String memberName;  // 누가 입찰했는지 표시용
    private int bidPrice;
    private LocalDateTime bidTime;

    public static BidDTO toBidDTO(BidEntity bidEntity) {
        BidDTO bidDTO = new BidDTO();

        bidDTO.setId(bidEntity.getId());
        bidDTO.setBidPrice(bidEntity.getBidPrice());
        bidDTO.setBidTime(bidEntity.getBidTime());
        bidDTO.setAuctionId(bidEntity.getAuctionEntity().getId());
        bidDTO.setMemberId(bidEntity.getMemberEntity().getId());

        return bidDTO;
    }
}
