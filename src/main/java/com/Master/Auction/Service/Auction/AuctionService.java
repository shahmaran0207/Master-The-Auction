package com.Master.Auction.Service.Auction;

import com.Master.Auction.Repository.Auction.AuctionFileRepository;
import com.Master.Auction.Repository.Auction.AuctionRepository;
import com.Master.Auction.Repository.Member.MemberRepository;
import com.Master.Auction.Entity.Auction.AuctionFileEntity;
import com.Master.Auction.Entity.Auction.AuctionEntity;
import org.springframework.web.multipart.MultipartFile;
import com.Master.Auction.Entity.Member.MemberEntity;
import org.springframework.data.domain.PageRequest;
import com.Master.Auction.DTO.Auction.AuctionDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.io.IOException;
import java.io.File;

@Service
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final MemberRepository memberRepository;
    private final AuctionFileRepository auctionFileRepository;

    public Page<AuctionDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<AuctionEntity> boardEntities =
                auctionRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<AuctionDTO> auctionDTOS = boardEntities.map(auction ->
                new AuctionDTO(auction.getId(), auction.getAuctionTitle(), auction.getAuctionContents(),auction.getStartTime(),
                        auction.getEndTime(), auction.getStartPrice(), auction.getMinPrice(), auction.getMaxPrice(),
                        auction.getAuctionHits(), auction.getMemberEntity().getMemberName(), auction.getAuctionStatus()));
        return auctionDTOS;
    }

    public void save(AuctionDTO auctionDTO, LocalDateTime endTime, Long id) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        if (auctionDTO.getAuctionImage() == null) {
            AuctionEntity auctionEntity = AuctionEntity.toSaveEntity(auctionDTO, memberEntity, endTime);
            auctionRepository.save(auctionEntity);
        } else {
            MultipartFile auctionFile = auctionDTO.getAuctionImage();
            String originalFilename = auctionFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Master-The-Auction/Auction/" + storedFileName;
            auctionFile.transferTo(new File(savePath));

            AuctionEntity auctionEntity = AuctionEntity.toSaveEntity(auctionDTO, memberEntity, endTime);
            Long auctionId = auctionRepository.save(auctionEntity).getId();
            AuctionEntity savedAuctionEntity = auctionRepository.findById(auctionId).get();

            AuctionFileEntity auctionFileEntity = AuctionFileEntity.toAuctionFileEntity(savedAuctionEntity, originalFilename, storedFileName);
            auctionFileRepository.save(auctionFileEntity);
        }
    }
}