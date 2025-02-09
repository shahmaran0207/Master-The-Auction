package com.Master.Auction.Controller.QnA.Question;

import com.Master.Auction.Service.QnA.Question.QuestionService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.Master.Auction.DTO.QnA.Question.QuestionDTO;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;

@Controller
@RequestMapping("/QnA")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

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
    public String save(@ModelAttribute QuestionDTO questionDTO, HttpSession session) throws IOException {
        Long id = (Long) session.getAttribute("loginId");
        questionService.save(questionDTO, id);
        return "home";
    }

}
