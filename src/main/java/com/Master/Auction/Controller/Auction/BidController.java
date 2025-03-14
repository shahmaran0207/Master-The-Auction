package com.Master.Auction.Controller.Auction;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import com.Master.Auction.Service.Auction.BidService;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpServletRequest;
import com.Master.Auction.DTO.Auction.BidDTO;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.Cookie;

@Controller
@RequestMapping("/Bid")
@RequiredArgsConstructor
public class BidController {
    private final BidService bidService;

    @PostMapping("/bid")
    public String onbid(@ModelAttribute BidDTO bidDTO, HttpServletRequest request,
                        @RequestParam(name = "page", required = false, defaultValue = "1") int page) {

        int Money = bidDTO.getBidPrice();
        Long auction = bidDTO.getAuctionId();
        String loginId = getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        bidService.onbid(Money, auction, memberId);

        Long auctionId = bidDTO.getAuctionId();
        return "redirect:/Auction/" + auctionId + "?page=" + page;
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
}
