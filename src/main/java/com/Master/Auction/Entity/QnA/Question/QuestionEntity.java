package com.Master.Auction.Entity.QnA.Question;

import com.Master.Auction.Entity.QnA.Answer.AnswerEntity;
import com.Master.Auction.DTO.QnA.Question.QuestionDTO;
import com.Master.Auction.Entity.Member.MemberEntity;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="Question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String QuestionTitle;

    @Column
    private String questionPass;

    @Column(columnDefinition = "TEXT")
    private String QuestionContent;

    @Column
    private int questionhits;

    @Column
    private String answerStatus;

    @Column
    private int fileAttached;

    @Column
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<AnswerEntity> answersList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QuestionFileEntity> questionFileEntityList = new ArrayList<>();

    public static QuestionEntity toSaveEntity(QuestionDTO questionDTO, MemberEntity memberEntity) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setAnswerStatus("not answered");
        questionEntity.setQuestionPass(questionDTO.getQuestionPass());
        questionEntity.setQuestionTitle(questionDTO.getQuestionTitle());
        questionEntity.setQuestionContent(questionDTO.getQuestionContent());
        questionEntity.setCreateDate(LocalDateTime.now());
        questionEntity.setQuestionhits(0);
        questionEntity.setFileAttached(0);
        questionEntity.setMemberEntity(memberEntity);
        return questionEntity;
    }

    public static QuestionEntity toSaveFileEntity(QuestionDTO questionDTO, MemberEntity memberEntity) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setQuestionTitle(questionDTO.getQuestionTitle());
        questionEntity.setQuestionPass(questionDTO.getQuestionPass());
        questionEntity.setCreateDate(LocalDateTime.now());
        questionEntity.setQuestionContent(questionDTO.getQuestionContent());
        questionEntity.setAnswerStatus("not answered");
        questionEntity.setQuestionhits(0);
        questionEntity.setFileAttached(1);
        questionEntity.setMemberEntity(memberEntity);
        return questionEntity;
    }
}
