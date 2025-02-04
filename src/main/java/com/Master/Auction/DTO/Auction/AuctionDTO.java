package com.Master.Auction.DTO.Auction;

import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuctionDTO {

    private Long id;

    private String title;
    private String content;
    private String originalFileName;
    private String storedFileName;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private int StartPrice;
    private int MinPrice;
    private int MaxPrice;
    private int AuctionHits;

    private MultipartFile AuctionImage;
}
