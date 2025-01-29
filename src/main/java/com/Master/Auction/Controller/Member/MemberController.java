package com.Master.Auction.Controller.Member;

import com.Master.Auction.Service.Member.MemberService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.Master.Auction.DTO.Member.MemberDTO;
import lombok.RequiredArgsConstructor;
import java.io.IOException;

@Controller
@RequestMapping("/Member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/save")
    public String save() {
        return "Member/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberdto) throws IOException, FirebaseAuthException {
        memberService.save(memberdto);
        return "home";
    }

    @PostMapping("/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        String checkResult = memberService.emailCheck(memberEmail);
        if (checkResult != null) return "no";
        else return "ok";
    }

    @GetMapping("/login")
    public String login() {
        return "Member/login";
    }

}
