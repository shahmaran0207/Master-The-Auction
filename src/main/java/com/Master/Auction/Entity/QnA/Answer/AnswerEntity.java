package com.Master.Auction.Entity.QnA.Answer;

import com.Master.Auction.Entity.QnA.Question.QuestionEntity;
import com.Master.Auction.Entity.Member.MemberEntity;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Answer")
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity questionEntity;

    private String answerStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    public static AnswerEntity toSaveEntity(MemberEntity memberEntity, String content, QuestionEntity question) {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setAnswerStatus("answered");
        answerEntity.setContent(content);
        answerEntity.setQuestionEntity(question);
        answerEntity.setCreateDate(LocalDateTime.now());
        answerEntity.setMemberEntity(memberEntity);
        return answerEntity;
    }
}
