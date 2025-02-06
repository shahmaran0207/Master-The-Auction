package com.Master.Auction.Entity.Auction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="auction_file_table")
public class AuctionFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private AuctionEntity auctionEntity;

    public static AuctionFileEntity toAuctionFileEntity(AuctionEntity auctionEntity, String originalFileName, String storedFileName) {
        AuctionFileEntity auctionFileEntity = new AuctionFileEntity();
        auctionFileEntity.setOriginalFileName(originalFileName);
        auctionFileEntity.setStoredFileName(storedFileName);
        auctionFileEntity.setAuctionEntity(auctionEntity);
        return auctionFileEntity;
    }
}
