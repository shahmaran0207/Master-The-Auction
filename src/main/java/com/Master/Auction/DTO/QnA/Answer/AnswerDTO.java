package com.Master.Auction.DTO.QnA.Answer;

import com.Master.Auction.Entity.QnA.Question.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnswerDTO {

    private int id;

    private String content;
    private String memberName;
    private String answerStatus;

    private LocalDateTime createDate;

    private QuestionEntity questionEntity;

}