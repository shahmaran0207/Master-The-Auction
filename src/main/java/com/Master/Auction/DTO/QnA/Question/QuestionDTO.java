package com.Master.Auction.DTO.QnA.Question;

import com.Master.Auction.Entity.QnA.Question.QuestionEntity;
import com.Master.Auction.Entity.QnA.Answer.AnswerEntity;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionDTO {

    private Long id;
    private Long memberId;

    private int questionhits;
    private int fileAttached;

    private String QuestionTitle;
    private String QuestionContent;
    private String questionPass;
    private String memberName;
    private String answerStatus;
    private String originalFileName;
    private String storedFileName;

    private LocalDateTime createDate;

    private MultipartFile QuestionFile;

    private List<AnswerEntity> answersList = new ArrayList<>();

    public QuestionDTO(Long id, Long memberId, String QuestionTitle, String questionContent,String memberName, LocalDateTime createDate, int questionhits,
                       String answerStatus) {
        this.id = id;
        this.QuestionTitle = QuestionTitle;
        this.QuestionContent = questionContent;
        this.memberId = memberId;
        this.memberName = memberName;
        this.createDate = createDate;
        this.questionhits = questionhits;
        this.answerStatus = answerStatus;
    }

    public static QuestionDTO toQuestionDTO(QuestionEntity questionEntity) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(questionEntity.getId());
        questionDTO.setQuestionTitle(questionEntity.getQuestionTitle());
        questionDTO.setQuestionPass(questionEntity.getQuestionPass());
        questionDTO.setQuestionContent(questionEntity.getQuestionContent());
        questionDTO.setMemberId(questionEntity.getMemberEntity().getId());
        questionDTO.setMemberName(questionEntity.getMemberEntity().getMemberName());
        questionDTO.setCreateDate(questionEntity.getCreateDate());
        questionDTO.setQuestionhits(questionEntity.getQuestionhits());
        questionDTO.setAnswerStatus(questionEntity.getAnswerStatus());

        if (questionEntity.getFileAttached() == 0) {
            questionDTO.setFileAttached(questionEntity.getFileAttached());
        } else {
            questionDTO.setFileAttached(questionEntity.getFileAttached());
            questionDTO.setOriginalFileName(questionEntity.getQuestionFileEntityList().get(0).getOriginalFileName());
            questionDTO.setStoredFileName(questionEntity.getQuestionFileEntityList().get(0).getStoredFileName());
        }

        return questionDTO;
    }

    public static QuestionEntity toEntity(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(questionDTO.getId());
        return questionEntity;
    }
}