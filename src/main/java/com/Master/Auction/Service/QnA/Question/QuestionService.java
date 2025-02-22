package com.Master.Auction.Service.QnA.Question;

import com.Master.Auction.Repository.QnA.Question.QuestionFileRepository;
import com.Master.Auction.Repository.QnA.Question.QuestionRepository;
import com.Master.Auction.Entity.QnA.Question.QuestionFileEntity;
import com.Master.Auction.Entity.QnA.Question.QuestionEntity;
import com.Master.Auction.Repository.Member.MemberRepository;
import org.springframework.web.multipart.MultipartFile;
import com.Master.Auction.DTO.QnA.Question.QuestionDTO;
import com.Master.Auction.Entity.Member.MemberEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;
import java.io.File;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    private final QuestionFileRepository questionFileRepository;

    public Page<QuestionDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<QuestionEntity> questionEntities =
                questionRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<QuestionDTO> questionDTOS = questionEntities.map(question ->
                new QuestionDTO(question.getId(), question.getMemberEntity().getId(),question.getQuestionTitle(), question.getQuestionContent(),
                        question.getMemberEntity().getMemberName(),
                        question.getCreateDate(), question.getQuestionhits(), question.getAnswerStatus()));
        return questionDTOS;
    }

    public void save(QuestionDTO questionDTO, Long id) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        if (questionDTO.getQuestionFile() == null || questionDTO.getQuestionFile().isEmpty()) {
            QuestionEntity questionEntity = QuestionEntity.toSaveEntity(questionDTO, memberEntity);
            questionRepository.save(questionEntity);
        } else {
            MultipartFile questionFile = questionDTO.getQuestionFile();
            String originalFilename = questionFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Master-The-Auction/Question/" + storedFileName;

            questionFile.transferTo(new File(savePath));

            QuestionEntity questionEntity = QuestionEntity.toSaveFileEntity(questionDTO, memberEntity);
            Long savedId = questionRepository.save(questionEntity).getId();
            QuestionEntity savedQuestionEntity = questionRepository.findById(savedId)
                    .orElseThrow(() -> new IllegalStateException("Question entity not found after saving."));

            QuestionFileEntity questionFileEntity = QuestionFileEntity.toQuestionFileEntity(
                    savedQuestionEntity, originalFilename, storedFileName);
            questionFileRepository.save(questionFileEntity);
        }
    }

    @Transactional
    public void updateHits(Long id) {
        questionRepository.updateHits(id);
    }

    @Transactional
    public QuestionDTO findById(Long id) {
        Optional<QuestionEntity> optionalQuestionEntity = questionRepository.findById(id);
        if (optionalQuestionEntity.isPresent()) {
            QuestionEntity questionEntity = optionalQuestionEntity.get();
            return QuestionDTO.toQuestionDTO(questionEntity);
        } else {
            return null;
        }
    }

}
