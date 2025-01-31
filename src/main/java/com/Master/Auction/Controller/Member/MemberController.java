package com.Master.Auction.Controller.Member;

import com.Master.Auction.Service.Member.MemberService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.Master.Auction.DTO.Member.MemberDTO;
import org.springframework.http.ResponseEntity;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuth;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;
import java.util.Map;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request, HttpSession session) {
        String idToken = request.get("idToken");
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

            String email = decodedToken.getEmail();
            String firebaseUid = decodedToken.getUid();

            MemberDTO memberDTO = memberService.login(email);

            session.setAttribute("loginId", memberDTO.getId());
            session.setAttribute("memberRole", memberDTO.getRole());
            session.setAttribute("loginEmail", memberDTO.getMail());
            session.setAttribute("loginName", memberDTO.getMemberName());
            session.setAttribute("firebaseUid", firebaseUid);

            return ResponseEntity.ok("/");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("/Member/login");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginId");
        session.removeAttribute("memberRole");
        session.removeAttribute("firebaseUid");
        session.removeAttribute("loginName");
        session.removeAttribute("loginEmail");

        return "redirect:/";
    }

    @GetMapping("/myPage")
    public String myPage(HttpSession session, Model model) {
        Long memberId = (Long) session.getAttribute("loginId");

        if (memberId != null) {
            model.addAttribute("member", memberService.findById(memberId));
            return "Member/myPage";

        } else return "Member/login";
    }

}
