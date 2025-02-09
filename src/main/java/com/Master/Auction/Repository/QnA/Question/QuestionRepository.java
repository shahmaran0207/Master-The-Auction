package com.Master.Auction.Repository.QnA.Question;

import com.Master.Auction.Entity.QnA.Question.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
}
