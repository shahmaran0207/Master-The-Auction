package com.Master.Auction.Service.Auction;

import com.Master.Auction.DTO.Auction.AuctionDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuctionService {

    public Page<AuctionDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        return null;
    }
}
