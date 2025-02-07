package com.Master.Auction.DTO.Auction;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import com.Master.Auction.Entity.Auction.AuctionEntity;
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
    private Long memberId;

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
    private int fileAttached;

    private MultipartFile AuctionImage;

    public AuctionDTO(Long id, String AuctionTitle, String AuctionContent, LocalDateTime startTime, LocalDateTime endTime, int startPrice,
                      int minPrice, int maxPrice, int AuctionHits, String memberName, String auctionStatus, Long memberId){
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
        this.memberId = memberId;
    }

    public static AuctionDTO toAuctionDTO(AuctionEntity auctionEntity) {
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setId(auctionEntity.getId());
        auctionDTO.setAuctionTitle(auctionEntity.getAuctionTitle());
        auctionDTO.setAuctionContent(auctionEntity.getAuctionContents());
        auctionDTO.setAuctionHits(auctionEntity.getAuctionHits());
        auctionDTO.setAuctionStatus(auctionEntity.getAuctionStatus());
        auctionDTO.setEndTime(auctionEntity.getEndTime());
        auctionDTO.setStartPrice(auctionEntity.getStartPrice());
        auctionDTO.setMinPrice(auctionEntity.getMinPrice());
        auctionDTO.setMaxPrice(auctionEntity.getMaxPrice());
        auctionDTO.setMemberName(auctionEntity.getMemberEntity().getMemberName());
        auctionDTO.setMemberId(auctionEntity.getMemberEntity().getId());

        if (auctionEntity.getFileAttached() == 0) {
            auctionDTO.setFileAttached(auctionEntity.getFileAttached());
        } else {
            auctionDTO.setFileAttached(auctionEntity.getFileAttached());
            auctionDTO.setOriginalFileName(auctionEntity.getAuctionFileEntities().get(0).getOriginalFileName());
            auctionDTO.setStoredFileName(auctionEntity.getAuctionFileEntities().get(0).getStoredFileName());
        }
        return auctionDTO;
    }
}
