package com.Master.Auction.Service.Board;

import org.springframework.transaction.annotation.Transactional;
import com.Master.Auction.Repository.Board.BoardHateRepository;
import com.Master.Auction.Repository.Member.MemberRepository;
import com.Master.Auction.Repository.Board.BoardRepository;
import com.Master.Auction.Entity.Board.BoardHateEntity;
import com.Master.Auction.Entity.Member.MemberEntity;
import jakarta.persistence.EntityNotFoundException;
import com.Master.Auction.Entity.Board.BoardEntity;
import com.Master.Auction.DTO.Board.BoardHateDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardHateService {

    private final BoardHateRepository boardHateRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public String toggleHate(BoardHateDTO boardHateDTO) {
        Long boardId = boardHateDTO.getBoardId();
        Long memberId = boardHateDTO.getMemberId();

        if (boardId == null || memberId == null) {
            throw new IllegalArgumentException("Board ID and Member ID must not be null");
        }

        BoardEntity boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + boardId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        if (boardHateRepository.existsByMemberEntityAndBoardEntity(memberEntity, boardEntity)) {
            boardHateRepository.deleteByMemberEntityAndBoardEntity(memberEntity, boardEntity);
            boardEntity.decreaseHatesCount();
            boardRepository.save(boardEntity);
            return "Like removed";
        } else {
            BoardHateEntity hate = BoardHateEntity.toSaveEntity(memberEntity, boardEntity);
            boardHateRepository.save(hate);
            boardEntity.increaseHatesCount();
            boardRepository.save(boardEntity);
            return "Like added";
        }
    }

    public int getHateCount(Long boardId) {
        return boardHateRepository.countByBoardEntity(boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID")));
    }

    public boolean isHatedByMember(Long boardId, Long memberId) {
        BoardEntity boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + boardId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        return boardHateRepository.existsByMemberEntityAndBoardEntity(memberEntity, boardEntity);
    }
}
