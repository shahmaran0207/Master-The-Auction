package com.Master.Auction.Repository.QnA.Answer;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Master.Auction.Entity.QnA.Answer.AnswerEntity;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

    @Transactional
    void deleteByQuestionEntity_Id(Long id);
}
