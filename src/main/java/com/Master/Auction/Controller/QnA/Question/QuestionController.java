package com.Master.Auction.Controller.QnA.Question;

import com.Master.Auction.Service.QnA.Question.QuestionService;
import com.Master.Auction.Service.QnA.Answer.AnswerService;
import com.Master.Auction.DTO.QnA.Question.QuestionDTO;
import org.springframework.data.web.PageableDefault;
import com.Master.Auction.DTO.QnA.Answer.AnswerDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import jakarta.servlet.http.Cookie;
import java.io.IOException;

@Controller
@RequestMapping("/QnA")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("/list")
    public String QnAList(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<QuestionDTO> questionDTOLists = questionService.paging(pageable);
        int blockLimit = 10;
        int startPage = ((pageable.getPageNumber() / blockLimit) * blockLimit) + 1;
        int endPage = Math.min(startPage + blockLimit - 1, questionDTOLists.getTotalPages());

        model.addAttribute("questionEntityList", questionDTOLists);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "QnA/list";
    }

    @GetMapping("/save")
    public String QuestionSave() {
        return "QnA/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute QuestionDTO questionDTO, HttpServletRequest request) throws IOException {
        String loginId = getCookieValue(request, "loginId");
        Long id = (loginId != null) ? Long.valueOf(loginId) : null;
        questionService.save(questionDTO, id);
        return "home";
    }

    @GetMapping("/{id}")
    public String QuestionDetail(@CookieValue(value = "loginId", defaultValue = "") String loginId, @CookieValue(value = "loginName") String loginName,
    @PathVariable Long id, Model model, @CookieValue(value = "memberRole", defaultValue = "") String memberRole,
                                 @PageableDefault(page=1) Pageable pageable) {
        questionService.updateHits(id);
        QuestionDTO questionDTO = questionService.findById(id);
        AnswerDTO answerDTO = answerService.findByQuestionId(id);
        model.addAttribute("loginId", loginId);
        model.addAttribute("loginName", loginName);
        model.addAttribute("memberRole", memberRole);
        model.addAttribute("answer", answerDTO);
        model.addAttribute("question", questionDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "QnA/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
       // questionService.delete(id);
        return "redirect:/QnA/list";
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
