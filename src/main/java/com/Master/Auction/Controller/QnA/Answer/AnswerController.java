package com.Master.Auction.Controller.QnA.Answer;

import com.Master.Auction.Service.QnA.Question.QuestionService;
import com.Master.Auction.Entity.QnA.Question.QuestionEntity;
import com.Master.Auction.Service.QnA.Answer.AnswerService;
import com.Master.Auction.DTO.QnA.Question.QuestionDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/Answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/write/{id}")
    public String writeAnswer(@CookieValue(value = "loginId", defaultValue = "") String loginId,
                              @RequestBody Map<String, Object> requestData) throws IOException {
        Integer memberId = Integer.parseInt(loginId);
        String commentContents = (String) requestData.get("commentContents");
        Long questionid = Long.valueOf(requestData.get("Question").toString());
        QuestionDTO questionDTO = questionService.findById(questionid);
        QuestionEntity question = questionDTO.toEntity(questionDTO);
        Long member = memberId.longValue();
        answerService.save(member, commentContents, question);

        return "home";
    }
}
