package com.Master.Auction.Entity.Auction;

import com.Master.Auction.Entity.Member.MemberEntity;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bid_table")
public class BidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int bidPrice;

    @Column(nullable = false)
    private LocalDateTime bidTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", nullable = false)
    private AuctionEntity auctionEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    public static BidEntity toSaveEntity(int money, AuctionEntity auctionEntity, MemberEntity memberEntity) {
        BidEntity bidEntity = new BidEntity();
        bidEntity.setAuctionEntity(auctionEntity);
        bidEntity.setMemberEntity(memberEntity);
        bidEntity.setBidPrice(money);
        bidEntity.setBidTime(LocalDateTime.now());
        return bidEntity;
    }
}
