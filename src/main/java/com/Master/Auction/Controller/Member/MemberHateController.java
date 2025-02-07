package com.Master.Auction.Controller.Member;

import com.Master.Auction.Service.Member.MemberHateService;
import com.Master.Auction.DTO.Member.MemberHateDTO;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member_hates")
@RequiredArgsConstructor
public class MemberHateController {
    private final MemberHateService memberHateService;

       @PostMapping("/toggle")
       public String toggleHate(@RequestBody MemberHateDTO memberHateDTO) {
           return memberHateService.toggleHate(memberHateDTO);
       }

      @GetMapping("/count/{target_hater}")
      public int getHateCount(@PathVariable Long target_hater) {

        return memberHateService.getHateCount(target_hater);
        }

      @GetMapping("/status/{target_hater}/{HaterId}")
       public boolean checkHateStatus(@PathVariable Long HaterId, @PathVariable Long target_hater) {
             return memberHateService.isHatedByMember(HaterId, target_hater);
    }
}