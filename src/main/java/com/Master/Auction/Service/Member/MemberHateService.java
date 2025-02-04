package com.Master.Auction.Service.Member;

import com.Master.Auction.Repository.Member.MemberHateRepository;
import org.springframework.transaction.annotation.Transactional;
import com.Master.Auction.Repository.Member.MemberRepository;
import com.Master.Auction.Entity.Member.MemberHateEntity;
import com.Master.Auction.Entity.Member.MemberEntity;
import jakarta.persistence.EntityNotFoundException;
import com.Master.Auction.DTO.Member.MemberHateDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberHateService {

    private final MemberRepository memberRepository;
    private final MemberHateRepository memberHateRepository;

    @Transactional
    public String toggleHate(MemberHateDTO memberHateDTO) {
        Long targetId = memberHateDTO.getTargetHater();
        Long haterId = memberHateDTO.getHater();

        if (targetId == null || haterId == null) {
            throw new IllegalArgumentException("target ID and hater ID must not be null");
        }

        MemberEntity target = memberRepository.findById(targetId)
                .orElseThrow(() -> new EntityNotFoundException("target not found with id: " + targetId));
        MemberEntity hater = memberRepository.findById(haterId)
                .orElseThrow(() -> new EntityNotFoundException("hater not found with id: " + haterId));

        if (memberHateRepository.existsByHaterAndTargetHater(hater, target)) {
            memberHateRepository.deleteByHaterAndTargetHater(hater, target);
            target.decreaseHatesCount();
            memberRepository.save(target);
            return "Hate removed";
        } else {
            MemberHateEntity hate = MemberHateEntity.toSaveEntity(hater, target);

            memberHateRepository.save(hate);
            target.increaseHatesCount();
            memberRepository.save(target);
            return "Hate added";
        }
    }

    public int getHateCount(Long taregtId) {
        return memberHateRepository.countByTargetHater(memberRepository.findById(taregtId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid taregt ID")));
    }

    public boolean isHatedByMember(Long targetId, Long haterId) {
        MemberEntity target = memberRepository.findById(targetId)
                .orElseThrow(() -> new EntityNotFoundException("target not found with id: " + targetId));
        MemberEntity hater = memberRepository.findById(haterId)
                .orElseThrow(() -> new EntityNotFoundException("hater not found with id: " + haterId));

        return memberHateRepository.existsByHaterAndTargetHater(hater, target);
    }
}
