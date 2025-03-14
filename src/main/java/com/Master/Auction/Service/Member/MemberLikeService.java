package com.Master.Auction.Service.Member;

import com.Master.Auction.Repository.Member.MemberLikeRepository;
import org.springframework.transaction.annotation.Transactional;
import com.Master.Auction.Repository.Member.MemberRepository;
import com.Master.Auction.Entity.Member.MemberLikeEntity;
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

        Long likerId = memberDTO.getLiker();
        Long targetId = memberDTO.getTarget();

        if( likerId==null || targetId==null ) {
            throw new IllegalArgumentException("liker ID and target ID must not be null");
        }

        MemberEntity liker = memberRepository.findById(likerId)
                .orElseThrow(() -> new EntityNotFoundException("liker not found with Id: " + likerId));

        MemberEntity target = memberRepository.findById(likerId)
                .orElseThrow(() -> new EntityNotFoundException("target not found with Id: " + targetId));

        if (memberLikeRepository.existsByLikerAndTarget(liker, target)) {
            memberLikeRepository.deleteByLikerAndTarget(liker, target);
            liker.decreaseLikesCount();
            memberRepository.save(liker);
            return "Like removed";
        } else {
            MemberLikeEntity like = MemberLikeEntity.toSaveEntity(liker, target);
            memberLikeRepository.save(like);
            liker.increaseLikesCount();
            memberRepository.save(target);
            return "Like added";
        }
     }

    public int getLikeCount(Long memberId) {
        return memberLikeRepository.countByTarget(memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID")));
    }

    public boolean isLikedByMember(Long target, Long liker) {
        MemberEntity targetId = memberRepository.findById(target)
                .orElseThrow(() -> new EntityNotFoundException("target not found with id: " + target));
        MemberEntity likerId = memberRepository.findById(liker)
                .orElseThrow(() -> new EntityNotFoundException("liker not found with id: " + liker));

        return memberLikeRepository.existsByLikerAndTarget(likerId, targetId);
    }
}
