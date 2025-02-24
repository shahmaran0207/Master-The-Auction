package com.Master.Auction.Controller.PayMent;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping("PayMent")
public class PayMentController {

    @GetMapping("payment")
    public String payment(@CookieValue(value = "loginName", defaultValue = "") String loginName,
                          @CookieValue(value = "loginEmail", defaultValue = "") String loginEmail, Model model) {
        model.addAttribute("loginEmail", loginEmail);
        model.addAttribute("loginName", loginName);
        return "PayMent/payment";
    }

    @PostMapping("/charge")
    public String charge(){
        return "PayMent/payment";
    }
}
