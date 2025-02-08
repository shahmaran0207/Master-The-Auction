package com.Master.Auction.Service.Board;

import com.Master.Auction.Repository.Board.BoardFileRepository;
import com.Master.Auction.Repository.Board.CommentRepository;
import com.Master.Auction.Repository.Member.MemberRepository;
import com.Master.Auction.Repository.Board.BoardRepository;
import com.Master.Auction.Entity.Board.BoardFileEntity;
import org.springframework.web.multipart.MultipartFile;
import com.Master.Auction.Entity.Member.MemberEntity;
import com.Master.Auction.Entity.Board.BoardEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.Master.Auction.DTO.Board.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;
import java.util.List;
import java.io.File;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardFileRepository boardFileRepository;
    private final CommentRepository commentRepository;

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<BoardDTO> boardDTOS = boardEntities.map(board ->
                new BoardDTO(board.getId(), board.getBoardTitle(), board.getBoardHits(), board.getBoardCreatedTime(),
                        board.getMemberEntity().getMemberName(), board.getLikesCount(), board.getMemberEntity().getId()));
        return boardDTOS;
    }

    public void save(BoardDTO boardDTO, Long id) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        if (boardDTO.getBoardFile().isEmpty()) {
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, memberEntity);
            boardRepository.save(boardEntity);
        } else {
            MultipartFile boardFile = boardDTO.getBoardFile();
            String originalFilename = boardFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Master-The-Auction/Board/" + storedFileName;
            boardFile.transferTo(new File(savePath));

            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO, memberEntity);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity savedBoardEntity = boardRepository.findById(savedId).get();

            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(savedBoardEntity, originalFilename, storedFileName);
            boardFileRepository.save(boardFileEntity);
        }
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            return BoardDTO.toBoardDTO(boardEntity);
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(Long id) {
        List<BoardFileEntity> boardFiles = boardFileRepository.findByBoardEntity_Id(id);

        if(boardFiles.isEmpty()) {
            commentRepository.deleteByBoardEntity_Id(id);
            boardRepository.deleteById(id);
        }
        else {
            for (BoardFileEntity file : boardFiles) {
                String filePath = "/Board/" + file.getStoredFileName(); // 저장된 파일 경로
                File fileToDelete = new File(filePath);
                if (fileToDelete.exists()) {
                    fileToDelete.delete(); // 실제 파일 삭제
                }
                boardFileRepository.deleteByBoardEntity_Id(id);
                commentRepository.deleteByBoardEntity_Id(id);
                boardRepository.deleteById(id);
            }
        }
    }

    public BoardDTO update(BoardDTO boardDTO, Long memberId) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberId));

        // 기존 게시글 조회 (없으면 예외 발생)
        BoardEntity existingBoardEntity = boardRepository.findById(boardDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID: " + boardDTO.getId()));

        // 파일이 없을 경우 내용만 업데이트
        if (boardDTO.getBoardFile().isEmpty()) {
            existingBoardEntity.setBoardPass(boardDTO.getBoardPass());
            existingBoardEntity.setBoardTitle(boardDTO.getBoardTitle());
            existingBoardEntity.setBoardContents(boardDTO.getBoardContents());
            existingBoardEntity.setBoardHits(boardDTO.getBoardHits());
            existingBoardEntity.setLikesCount(boardDTO.getLikesCount());
            existingBoardEntity.setHatesCount(boardDTO.getHatesCount());
            existingBoardEntity.setMemberEntity(memberEntity);
        } else {
            MultipartFile boardFile = boardDTO.getBoardFile();
            String originalFilename = boardFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Master-The-Auction/Board/" + storedFileName;
            boardFile.transferTo(new File(savePath));

            // 파일 정보 업데이트
            existingBoardEntity.setBoardPass(boardDTO.getBoardPass());
            existingBoardEntity.setFileAttached(1);
            existingBoardEntity.setBoardTitle(boardDTO.getBoardTitle());
            existingBoardEntity.setBoardContents(boardDTO.getBoardContents());
            existingBoardEntity.setBoardHits(boardDTO.getBoardHits());
            existingBoardEntity.setLikesCount(boardDTO.getLikesCount());
            existingBoardEntity.setHatesCount(boardDTO.getHatesCount());
            existingBoardEntity.setMemberEntity(memberEntity);

            // 파일 엔티티 생성 및 저장
            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(existingBoardEntity, originalFilename, storedFileName);
            boardFileRepository.save(boardFileEntity);
        }

        // 변경 내용 저장 (JPA가 변경 감지하여 UPDATE 수행)
        boardRepository.save(existingBoardEntity);
        return findById(boardDTO.getId());
    }

}
