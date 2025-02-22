package com.Master.Auction.DTO.QnA.Answer;

import com.Master.Auction.Entity.QnA.Question.QuestionEntity;
import com.Master.Auction.Entity.QnA.Answer.AnswerEntity;
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

    public static AnswerDTO toAnswerDTO(AnswerEntity answerEntity) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(answerEntity.getId());
        answerDTO.setContent(answerEntity.getContent());
        answerDTO.setCreateDate(answerEntity.getCreateDate());
        answerDTO.setMemberName(answerEntity.getMemberEntity().getMemberName());
        answerDTO.setAnswerStatus(answerEntity.getAnswerStatus());

        return answerDTO;
    }
}