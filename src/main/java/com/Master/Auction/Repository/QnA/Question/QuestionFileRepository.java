package com.Master.Auction.Repository.QnA.Question;

import com.Master.Auction.Entity.QnA.Question.QuestionFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionFileRepository extends JpaRepository<QuestionFileEntity, Long> {

    List<QuestionFileEntity> findByQuestionEntity_Id(Long questionEntityId);
}
