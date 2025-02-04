package com.Master.Auction.Entity.Auction;

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
    private int MaxPrice;

    @OneToMany(mappedBy = "auctionEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AuctionFileEntity> auctionFileEntities = new ArrayList<>();
}
