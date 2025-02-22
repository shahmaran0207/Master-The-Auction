package com.Master.Auction.Repository.QnA.Question;

import com.Master.Auction.Entity.QnA.Question.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.QnA.Answer.AnswerEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update QuestionEntity q set q.questionhits = q.questionhits + 1 where q.id = :id")
    void updateHits(@Param("id") Long id);

    @Query("SELECT a FROM AnswerEntity a WHERE a.questionEntity.id = :id")
    Optional<AnswerEntity> findByQuestionId(@Param("id") Long id);
}
