package com.Master.Auction.Controller.PayMent;

import com.Master.Auction.Service.Member.MemberService;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.Master.Auction.DTO.Member.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping("/PayMent")
public class PayMentController {
    private final MemberService memberService;

    @GetMapping("/payment")
    public String payment(@CookieValue(value = "loginName", defaultValue = "") String loginName,
                          @CookieValue(value = "loginEmail", defaultValue = "") String loginEmail, Model model) {
        model.addAttribute("loginEmail", loginEmail);
        model.addAttribute("loginName", loginName);
        return "PayMent/payment";
    }

    @PostMapping("/charge")
    public String chargePoint(@RequestBody MemberDTO memberDTO,
                              @CookieValue(value = "loginEmail", defaultValue = "") String loginEmail,
                              @CookieValue(value = "loginId", defaultValue = "") String loginId) {

        Long memberId = Long.parseLong(loginId);

        memberService.buyPoint(memberId, memberDTO);

        return "redirect:/Member/myPage";
    }
}
