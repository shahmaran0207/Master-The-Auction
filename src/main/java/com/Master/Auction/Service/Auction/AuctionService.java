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
import com.Master.Auction.Service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final MemberRepository memberRepository;
    private final ImageService imageService;
    private final AuctionFileRepository auctionFileRepository;

    public Page<AuctionDTO> paging(Pageable pageable, String status) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<AuctionEntity> auctionEntities;

        PageRequest pageRequest = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));

        if (status == null || status.isEmpty()) {
            auctionEntities = auctionRepository.findAll(pageRequest);
        } else {
            auctionEntities = auctionRepository.findByAuctionStatus(status, pageRequest);
        }

        return auctionEntities.map(auction ->
                new AuctionDTO(auction.getId(), auction.getAuctionTitle(), auction.getAuctionContents(), auction.getStartTime(),
                        auction.getEndTime(), auction.getStartPrice(), auction.getMinPrice(), auction.getMaxPrice(),
                        auction.getAuctionHits(), auction.getMemberEntity().getMemberName(), auction.getAuctionStatus(), auction.getMemberEntity().getId()));
    }



    public void save(AuctionDTO auctionDTO, LocalDateTime endTime, Long id) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        if (auctionDTO.getAuctionImage() == null) {
            AuctionEntity auctionEntity = AuctionEntity.toSaveEntity(auctionDTO, memberEntity, endTime);
            auctionRepository.save(auctionEntity);
        } else {
            MultipartFile auctionFile = auctionDTO.getAuctionImage();

            String s3Url = imageService.imageUploadFromFile(auctionFile);

            String originalFilename = auctionFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

            AuctionEntity auctionEntity = AuctionEntity.toSaveFileEntity(auctionDTO, memberEntity, endTime);
            Long auctionId = auctionRepository.save(auctionEntity).getId();
            AuctionEntity savedAuctionEntity = auctionRepository.findById(auctionId).get();

            AuctionFileEntity auctionFileEntity = AuctionFileEntity.toAuctionFileEntity(savedAuctionEntity, storedFileName, s3Url);
            auctionFileRepository.save(auctionFileEntity);
        }
    }

    @Transactional
    public void updateHits(Long id) {
        auctionRepository.updateHits(id);
    }

    @Transactional
    public AuctionDTO findById(Long id) {
        Optional<AuctionEntity> optionalBoardEntity = auctionRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            AuctionEntity boardEntity = optionalBoardEntity.get();
            return AuctionDTO.toAuctionDTO(boardEntity);
        } else {
            return null;
        }
    }

    public Page<AuctionDTO> AuctionList(Pageable pageable, Long id) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<AuctionEntity> auctionEntities;

            auctionEntities = auctionRepository.findByMemberEntity_Id(id,
                    PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"))
            );

        return auctionEntities.map(auction ->
                new AuctionDTO(auction.getId(), auction.getAuctionTitle(), auction.getAuctionContents(), auction.getStartTime(),
                        auction.getEndTime(), auction.getStartPrice(), auction.getMinPrice(), auction.getMaxPrice(),
                        auction.getAuctionHits(), auction.getMemberEntity().getMemberName(), auction.getAuctionStatus(),
                        auction.getMemberEntity().getId()));
    }
}