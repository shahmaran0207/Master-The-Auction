package com.Master.Auction.Service.Auction;

import com.Master.Auction.Repository.Auction.AuctionRepository;
import com.Master.Auction.Repository.Member.MemberRepository;
import com.Master.Auction.Repository.Auction.BidRepository;
import com.Master.Auction.Entity.Auction.AuctionEntity;
import com.Master.Auction.Entity.Member.MemberEntity;
import com.Master.Auction.Entity.Auction.BidEntity;
import org.springframework.stereotype.Service;
import com.Master.Auction.DTO.Auction.BidDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final MemberRepository memberRepository;

    public void onbid(int money, Long auction, Long memberId) {

        AuctionEntity auctionEntity = auctionRepository.findById(auction)
                .orElseThrow(() -> new IllegalArgumentException("Invalid auction id: " + auction));

        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id: " + memberId));

        BidEntity bidEntity = bidRepository.findByAuctionEntity(auctionEntity);

        if(bidEntity == null) {
            BidEntity newBid = BidEntity.toSaveEntity(money, auctionEntity, memberEntity);
            memberEntity.setMoney(memberEntity.getMoney() - money);
            memberRepository.save(memberEntity);
            bidRepository.save(newBid);
        } else{
            bidRepository.delete(bidEntity);

            MemberEntity existingBidMember = bidEntity.getMemberEntity();
            int Money = bidEntity.getBidPrice();
            existingBidMember.setMoney(existingBidMember.getMoney() + Money);

            BidEntity newBid = BidEntity.toSaveEntity(money, auctionEntity, memberEntity);
            memberEntity.setMoney(memberEntity.getMoney() - money);
            memberRepository.save(memberEntity);
            bidRepository.save(newBid);
        }

    }

    public BidDTO findByAuctionId(Long AuctionId) {
        AuctionEntity auctionEntity = auctionRepository.findById(AuctionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid auction id: " + AuctionId));
        BidEntity bidEntity = bidRepository.findByAuctionEntity(auctionEntity);
        if (bidEntity==null) return null;
        else{
            return BidDTO.toBidDTO(bidEntity);
        }
    }
}
