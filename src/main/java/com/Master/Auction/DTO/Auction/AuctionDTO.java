package com.Master.Auction.DTO.Auction;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuctionDTO {

    private Long id;

    private String AuctionTitle;
    private String AuctionContent;
    private String originalFileName;
    private String storedFileName;
    private String memberName;
    private String auctionStatus;

    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    private int StartPrice;
    private int MinPrice;
    private int MaxPrice;
    private int AuctionHits;

    private MultipartFile AuctionImage;

    public AuctionDTO(Long id, String AuctionTitle, String AuctionContent, LocalDateTime startTime, LocalDateTime endTime, int startPrice,
                      int minPrice, int maxPrice, int AuctionHits, String memberName, String auctionStatus){
        this.id = id;
        this.AuctionTitle = AuctionTitle;
        this.AuctionContent = AuctionContent;
        this.MinPrice = minPrice;
        this.MaxPrice = maxPrice;
        this.AuctionHits = AuctionHits;
        this.memberName = memberName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.StartPrice = startPrice;
        this.auctionStatus = auctionStatus;

    }
}
