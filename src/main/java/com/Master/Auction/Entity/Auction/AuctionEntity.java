package com.Master.Auction.Entity.Auction;

import com.Master.Auction.Entity.Member.MemberEntity;
import com.Master.Auction.DTO.Auction.AuctionDTO;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="auction_table")
public class AuctionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String AuctionTitle;

    @Column(length = 500)
    private String AuctionContents;

    @Column
    private int AuctionHits;

    @Column
    private int fileAttached;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private int MinPrice;

    @Column
    private int StartPrice;

    @Column
    private int MaxPrice;

    @Column
    private String auctionStatus;

    @OneToMany(mappedBy = "auctionEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BidEntity> bids = new ArrayList<>();


    @OneToMany(mappedBy = "auctionEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AuctionFileEntity> auctionFileEntities = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    public static AuctionEntity toSaveEntity(AuctionDTO auctionDTO, MemberEntity memberEntity, LocalDateTime endTime) {
        AuctionEntity auctionEntity = new AuctionEntity();
        auctionEntity.setAuctionTitle(auctionDTO.getAuctionTitle());
        auctionEntity.setAuctionContents(auctionDTO.getAuctionContent());
        auctionEntity.setAuctionHits(0);
        auctionEntity.setFileAttached(0);
        auctionEntity.setStartTime(LocalDateTime.now());
        auctionEntity.setEndTime(endTime);
        auctionEntity.setMinPrice(auctionDTO.getStartPrice());
        auctionEntity.setStartPrice(auctionDTO.getStartPrice());
        auctionEntity.setMaxPrice(auctionDTO.getStartPrice()+1);
        auctionEntity.setAuctionStatus("not finished");
        auctionEntity.setMemberEntity(memberEntity);
        return auctionEntity;
    }

    public static AuctionEntity toSaveFileEntity(AuctionDTO auctionDTO, MemberEntity memberEntity, LocalDateTime endTime) {
        AuctionEntity auctionEntity = new AuctionEntity();
        auctionEntity.setAuctionTitle(auctionDTO.getAuctionTitle());
        auctionEntity.setAuctionContents(auctionDTO.getAuctionContent());
        auctionEntity.setAuctionHits(0);
        auctionEntity.setFileAttached(1);
        auctionEntity.setStartTime(LocalDateTime.now());
        auctionEntity.setEndTime(endTime);
        auctionEntity.setMinPrice(auctionDTO.getStartPrice());
        auctionEntity.setStartPrice(auctionDTO.getStartPrice());
        auctionEntity.setMaxPrice(auctionDTO.getStartPrice()+1);
        auctionEntity.setAuctionStatus("not finished");
        auctionEntity.setMemberEntity(memberEntity);

        return auctionEntity;
    }
}


