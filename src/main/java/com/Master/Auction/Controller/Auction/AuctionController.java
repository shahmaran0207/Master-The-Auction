package com.Master.Auction.Controller.Auction;

import org.springframework.web.bind.annotation.*;
import com.Master.Auction.Service.Auction.AuctionService;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import com.Master.Auction.DTO.Auction.AuctionDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Auction")
public class AuctionController {
    private final AuctionService auctionService;

    @GetMapping("/save")
    public String save() {
        return "Auction/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute AuctionDTO auctionDTO,
                       @RequestParam("endDate") String endDate,
                       @RequestParam("endTimePicker") String endTimePicker,
                       HttpSession session) throws IOException {
        // endDate: "2025-02-07", endTimePicker: "00:00"
        // ISO-8601 형식("yyyy-MM-ddTHH:mm")으로 파싱하기 위해 날짜와 시간 문자열을 합칩니다.
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T" + endTimePicker);
        auctionDTO.setEndTime(endDateTime);

        Long id = (Long) session.getAttribute("loginId");
        System.out.println(auctionDTO);
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
}
