package com.Master.Auction.Controller.Member;

import com.Master.Auction.Service.Auction.WinningBidService;
import com.Master.Auction.Service.Auction.AuctionService;
import com.Master.Auction.Service.Member.MemberService;
import com.google.firebase.auth.FirebaseAuthException;
import com.Master.Auction.Service.Auction.BidService;
import org.springframework.data.web.PageableDefault;
import com.Master.Auction.DTO.Auction.AuctionDTO;
import com.Master.Auction.DTO.Auction.WinningDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import jakarta.servlet.http.HttpServletResponse;
import com.Master.Auction.DTO.Member.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.data.domain.Page;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import jakarta.servlet.http.Cookie;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/Member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BidService bidService;
    private final AuctionService auctionService;
    private final WinningBidService winningBidService;

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
    public ResponseEntity<String> login(@RequestBody Map<String, String> request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        String idToken = request.get("idToken");
        try {
            // 1. 쿠키에서 기존 로그인 상태 확인

            String existingLoginId = getCookieValue(httpRequest, "loginId");

            if (existingLoginId != null) {
                // 이미 로그인 상태라면 새로 로그인하지 않고 바로 성공 응답
                return ResponseEntity.ok("/"); // 이미 로그인된 사용자
            }

            // 2. Firebase 토큰 검증
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String email = decodedToken.getEmail();
            String firebaseUid = decodedToken.getUid();

            // 3. 사용자 정보 조회
            MemberDTO memberDTO = memberService.login(email);
            // 4. HttpOnly 쿠키로 사용자 정보 저장
            setHttpOnlyCookie(httpResponse, "loginId", memberDTO.getId().toString());
            setHttpOnlyCookie(httpResponse, "memberRole", String.valueOf(memberDTO.getRole()));
            setHttpOnlyCookie(httpResponse, "loginEmail", memberDTO.getMail());
            setHttpOnlyCookie(httpResponse, "loginName", memberDTO.getMemberName());
            setHttpOnlyCookie(httpResponse, "firebaseUid", firebaseUid);

            return ResponseEntity.ok("/"); // 로그인 성공
        } catch (Exception e) {
            return ResponseEntity.status(401).body("/Member/login"); // 로그인 실패
        }
    }

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        String loginId = getCookieValue(request, "loginId");
        model.addAttribute("isLoggedIn", loginId != null);

        return "home";
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // HttpOnly 쿠키 설정 유틸리티 메서드
    private void setHttpOnlyCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true); // JavaScript에서 접근 불가
        cookie.setSecure(true);  // HTTPS에서만 전송
        cookie.setPath("/");     // 모든 경로에서 유효
        cookie.setMaxAge(3600);  // 쿠키 유효시간: 1시간 (단위: 초)

        response.addCookie(cookie);
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        deleteCookie(response, "loginId");
        deleteCookie(response, "memberRole");
        deleteCookie(response, "firebaseUid");
        deleteCookie(response, "loginName");
        deleteCookie(response, "loginEmail");

        return "redirect:/";
    }

    @GetMapping("/myPage")
    public String myPage(Model model, HttpServletRequest request) {
        String loginId = getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        if (memberId != null) {
            model.addAttribute("member", memberService.findById(memberId));
            return "Member/myPage";

        } else return "Member/login";
    }

    @GetMapping("/list")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<MemberDTO> memberList = memberService.paging(pageable);
        int blockLimit = 10;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < memberList.getTotalPages()) ? startPage + blockLimit - 1 : memberList.getTotalPages();

        model.addAttribute("memberList", memberList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "Member/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model, @CookieValue(value = "loginId", defaultValue = "") String loginId,
                           @CookieValue(value = "loginName", defaultValue = "") String loginName,
                           @PageableDefault(page=1) Pageable pageable) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        model.addAttribute("loginId", loginId);
        model.addAttribute("loginName", loginName);
        model.addAttribute("page", pageable.getPageNumber());
        return "Member/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@CookieValue(value = "firebaseUid", defaultValue = "") String firebaseUid,
                         @PathVariable Long id, HttpSession session, HttpServletResponse response) {
        try {
            if (firebaseUid != null) {
                FirebaseAuth.getInstance().deleteUser(firebaseUid);
            }
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }

        deleteCookie(response, "loginId");
        deleteCookie(response, "memberRole");
        deleteCookie(response, "firebaseUid");
        deleteCookie(response, "loginName");
        deleteCookie(response, "loginEmail");

        memberService.deleteById(id);

        return "redirect:/";
    }

    // 쿠키 삭제 유틸리티 메서드
    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 쿠키 즉시 만료
        response.addCookie(cookie);
    }

    @GetMapping("/update/{id}")
    public String updateForm(@CookieValue(value = "loginId", defaultValue = "") String loginId, @PathVariable Long id, Model model) {
        model.addAttribute("loginId", loginId);
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "Member/update";
    }

    @PostMapping("/update")
    public String update(@CookieValue(value = "firebaseUid", defaultValue = "") String firebaseUid,
                         @ModelAttribute MemberDTO memberDTO, HttpServletRequest request,
                         Model model) throws IOException, FirebaseAuthException {
        try {
            if (firebaseUid != null) {
                FirebaseAuth.getInstance().deleteUser(firebaseUid);
            }
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        memberService.update(memberDTO);

        model.addAttribute("member", memberDTO);
        return "Member/myPage";
    }

    @GetMapping("/buyList/{id}")
    public String buyList(@PageableDefault(page = 1) Pageable pageable, @CookieValue(value = "loginId", defaultValue = "") String loginId,
                          @PathVariable Long id, Model model) {

        Page<WinningDTO> auctionList = winningBidService.WinningBidList(pageable, id);

        int blockLimit = 10;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < auctionList.getTotalPages()) ? startPage + blockLimit - 1 : auctionList.getTotalPages();

        model.addAttribute("loginId", loginId);
        model.addAttribute("BuyList", auctionList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "Member/buyList";
    }

    @GetMapping("/buyDetail/{id}")
    public String buyDetail(@PageableDefault(page = 1) Pageable pageable, @CookieValue(value = "loginId", defaultValue = "") String loginId,
                          @PathVariable Long id, Model model) {

        AuctionDTO auctionDTO = auctionService.findById(id);
        Long auctionId = auctionDTO.getId();

        WinningDTO winningDTO = winningBidService.findByAuctionId(auctionId);

        model.addAttribute("auction", auctionDTO);
        model.addAttribute("bidPrice", winningDTO.getWinningPrice());
        model.addAttribute("page", pageable.getPageNumber());

        return "Member/buyDetail";
    }

}
