package com.Master.Auction.Controller.Auction;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import com.Master.Auction.DTO.Auction.AuctionDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Auction")
public class AuctionController {

    @GetMapping("/save")
    public String save() {
        return "Auction/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute AuctionDTO auctionDTO, HttpSession session) throws IOException {
        Long id = (Long) session.getAttribute("loginId");
        return "home";
    }
}
