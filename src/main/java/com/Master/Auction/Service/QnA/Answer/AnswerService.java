package com.Master.Auction.Service.QnA.Answer;

import com.Master.Auction.Repository.QnA.Question.QuestionRepository;
import com.Master.Auction.Repository.QnA.Answer.AnswerRepository;
import com.Master.Auction.Repository.Member.MemberRepository;
import com.Master.Auction.Entity.QnA.Question.QuestionEntity;
import com.Master.Auction.Entity.Member.MemberEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final MemberRepository memberRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public void save(Long id, String contents, QuestionEntity question) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

       // AnswerEntity answerEntity = AnswerEntity.toSaveEntity(memberEntity, contents, question);
       // answerRepository.save(answerEntity);

        QuestionEntity existingQuestion = questionRepository.findById(question.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID: " + question.getId()));

        existingQuestion.setAnswerStatus("answered");
        questionRepository.save(existingQuestion);
    }

}
