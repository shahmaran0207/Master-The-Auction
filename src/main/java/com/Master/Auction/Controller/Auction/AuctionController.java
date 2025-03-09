package com.Master.Auction.Controller.Auction;

import com.Master.Auction.Service.Auction.AuctionService;
import com.Master.Auction.Service.Member.MemberService;
import com.Master.Auction.Service.Auction.BidService;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.Master.Auction.DTO.Auction.AuctionDTO;
import org.springframework.data.domain.Pageable;
import jakarta.servlet.http.HttpServletRequest;
import com.Master.Auction.DTO.Member.MemberDTO;
import com.Master.Auction.DTO.Auction.BidDTO;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import jakarta.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Auction")
public class AuctionController {
    private final AuctionService auctionService;
    private final MemberService memberService;
    private final BidService bidService;

    @GetMapping("/save")
    public String save() {
        return "Auction/save";
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

    @PostMapping("/save")
    public String save(@ModelAttribute AuctionDTO auctionDTO,
                       @RequestParam("endDate") String endDate,
                       @RequestParam("endTimePicker") String endTimePicker,
                       HttpServletRequest request) throws IOException {
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T" + endTimePicker);
        auctionDTO.setEndTime(endDateTime);

        String loginId = getCookieValue(request, "loginId");
        Long id = (loginId != null) ? Long.valueOf(loginId) : null;
        auctionService.save(auctionDTO, endDateTime, id);
        return "home";
    }

    @GetMapping("/list")
    public String paging(@PageableDefault(page = 1) Pageable pageable,
                         @RequestParam(value = "status", required = false) String status,
                         Model model) {

        Page<AuctionDTO> auctionList = auctionService.paging(pageable, status);

        int blockLimit = 10;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < auctionList.getTotalPages()) ? startPage + blockLimit - 1 : auctionList.getTotalPages();

        model.addAttribute("AuctionList", auctionList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("status", status); // 그냥 status로 넘기는 게 편함
        return "Auction/list";
    }

    @GetMapping("/{id}")
    public String findById(HttpServletRequest request,
                           @PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        auctionService.updateHits(id);
        String loginId = getCookieValue(request, "loginId");

        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;
        MemberDTO memberDTO = memberService.findById(memberId);
        AuctionDTO auctionDTO = auctionService.findById(id);

        BidDTO bidDTO =bidService.findByAuctionId(id);

        if (bidDTO != null) {
            model.addAttribute("bidPrice", bidDTO.getBidPrice());
        }  else{
            model.addAttribute("bidPrice", 0);
        }

        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("auction", auctionDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "Auction/detail";
    }

    @GetMapping("/AuctionList/{id}")
    public String AuctionList(@PageableDefault(page = 1) Pageable pageable, @PathVariable Long id,
                         Model model) {

        Page<AuctionDTO> auctionList = auctionService.AuctionList(pageable, id);

        int blockLimit = 10;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < auctionList.getTotalPages()) ? startPage + blockLimit - 1 : auctionList.getTotalPages();

        model.addAttribute("AuctionList", auctionList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "Auction/AuctionList";
    }
}
