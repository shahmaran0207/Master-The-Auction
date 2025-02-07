package com.Master.Auction.DTO.Board;

import org.springframework.web.multipart.MultipartFile;
import com.Master.Auction.Entity.Board.BoardEntity;
import java.time.LocalDateTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BoardDTO {

    private Long id;
    private Long memberId;

    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private String originalFileName;
    private String storedFileName;
    private String memberName;

    private int boardHits;
    private int fileAttached;
    private int likesCount;
    private int hatesCount;

    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    private MultipartFile boardFile;

    public BoardDTO(Long id, String boardTitle, int boardHits, LocalDateTime boardCreatedTime, String memberName, int likesCount, Long memberId) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
        this.memberName = memberName;
        this.likesCount =likesCount;
        this.memberId = memberId;
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getBoardCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getBoardUpdatedTime());
        boardDTO.setLikesCount((boardEntity.getLikesCount()));
        boardDTO.setHatesCount(boardEntity.getHatesCount());

        boardDTO.setMemberName(boardEntity.getMemberEntity().getMemberName());

        if (boardEntity.getFileAttached() == 0) {
            boardDTO.setFileAttached(boardEntity.getFileAttached());
        } else {
            boardDTO.setFileAttached(boardEntity.getFileAttached());
            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
        }
        return boardDTO;
    }
}
