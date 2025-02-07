package com.Master.Auction.Service.Board;

import com.Master.Auction.Repository.Board.CommentRepository;
import com.Master.Auction.Repository.Member.MemberRepository;
import com.Master.Auction.Repository.Board.BoardRepository;
import com.Master.Auction.Entity.Member.MemberEntity;
import com.Master.Auction.Entity.Board.CommentEntity;
import com.Master.Auction.Entity.Board.BoardEntity;
import com.Master.Auction.DTO.Board.CommentDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public List<CommentDTO> findAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);

        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    public Long save(CommentDTO commentDTO) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(commentDTO.getMemberId());

        if (optionalBoardEntity.isPresent() && optionalMemberEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            MemberEntity memberEntity = optionalMemberEntity.get();

            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity, memberEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }
}
