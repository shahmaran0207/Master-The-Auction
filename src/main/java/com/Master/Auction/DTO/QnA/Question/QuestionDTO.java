package com.Master.Auction.DTO.QnA.Question;

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
}