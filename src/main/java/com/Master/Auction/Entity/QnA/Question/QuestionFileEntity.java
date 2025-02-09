package com.Master.Auction.Entity.QnA.Question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="question_file_table")
public class QuestionFileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionEntity questionEntity;

    public static QuestionFileEntity toQuestionFileEntity(QuestionEntity questionEntity, String originalFileName, String storedFileName) {
        QuestionFileEntity questionFileEntity = new QuestionFileEntity();
        questionFileEntity.setOriginalFileName(originalFileName);
        questionFileEntity.setStoredFileName(storedFileName);
        questionFileEntity.setQuestionEntity(questionEntity);
        return questionFileEntity;
    }
}