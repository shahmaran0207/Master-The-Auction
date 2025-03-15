package com.Master.Auction.Entity.Auction;

import com.Master.Auction.Entity.Member.MemberEntity;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "winning_bid")
public class WinningBidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "auction_id", nullable = false)
    private AuctionEntity auction;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @Column(nullable = false)
    private int winningPrice;

    @Column(nullable = false)
    private LocalDateTime winningTime;

}
