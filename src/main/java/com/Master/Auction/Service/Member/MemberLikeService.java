package com.Master.Auction.Service.Member;

import com.Master.Auction.Repository.Member.MemberLikeRepository;
import org.springframework.transaction.annotation.Transactional;
import com.Master.Auction.Repository.Member.MemberRepository;
import com.Master.Auction.Entity.Member.MemberEntity;
import jakarta.persistence.EntityNotFoundException;
import com.Master.Auction.DTO.Member.MemberLikeDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MemberLikeService {
    private final MemberRepository memberRepository;
    private final MemberLikeRepository memberLikeRepository;

    @Transactional
    public String toggleLike(MemberLikeDTO memberDTO) {

        Long likerId = memberDTO.getMemberId();
        Long targetId = memberDTO.getAnotherMemberId();

        if( likerId==null || targetId==null ) {
            throw new IllegalArgumentException("liker ID and target ID must not be null");
        }

        MemberEntity liker = memberRepository.findById(likerId)
                .orElseThrow(() -> new EntityNotFoundException("liker not found with Id: " + likerId));

        MemberEntity target = memberRepository.findById(likerId)
                .orElseThrow(() -> new EntityNotFoundException("target not found with Id: " + targetId));


        return null;
     }
}
