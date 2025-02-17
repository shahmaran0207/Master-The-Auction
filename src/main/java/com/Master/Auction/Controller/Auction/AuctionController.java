package com.Master.Auction.Controller.Auction;

import com.Master.Auction.Service.Auction.AuctionService;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.Master.Auction.DTO.Auction.AuctionDTO;
import org.springframework.data.domain.Pageable;
import jakarta.servlet.http.HttpServletRequest;
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
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<AuctionDTO> AuctionList = auctionService.paging(pageable);
        int blockLimit = 10;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < AuctionList.getTotalPages()) ? startPage + blockLimit - 1 : AuctionList.getTotalPages();

        model.addAttribute("AuctionList", AuctionList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "Auction/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        auctionService.updateHits(id);

        AuctionDTO auctionDTO = auctionService.findById(id);

        model.addAttribute("auction", auctionDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "Auction/detail";
    }
}
