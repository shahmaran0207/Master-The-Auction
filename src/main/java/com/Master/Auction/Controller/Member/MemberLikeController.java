package com.Master.Auction.Controller.Member;

import com.Master.Auction.Service.Member.MemberLikeService;
import com.Master.Auction.DTO.Member.MemberLikeDTO;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member_likes")
@RequiredArgsConstructor
public class MemberLikeController {

    private final MemberLikeService memberLikeService;

    @PostMapping("/toggle")
    public String toggleLike(@RequestBody MemberLikeDTO memberLikeDTO) {
        System.out.println(memberLikeDTO);
        return memberLikeService.toggleLike(memberLikeDTO);
    }

    //   @GetMapping("/count/{memberId}")
    //   public int getLikeCount(@PathVariable Long memberId) {
//
    //      return .getLikeCount(memberId);
    //  }

    //   @GetMapping("/status/{memberId}/{memberId}")
    //   public boolean checkLikeStatus(@PathVariable Long LikerId, @PathVariable Long targetId) {
    //      return .isLikedByMember(LikerId, targetId);
    //  }

}

