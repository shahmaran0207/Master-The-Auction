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
        boardDTO.setMemberId(boardEntity.getMemberEntity().getId());

        if (boardEntity.getFileAttached() == 0) {
            boardDTO.setFileAttached(boardEntity.getFileAttached());
        } else {
            boardDTO.setFileAttached(boardEntity.getFileAttached());
            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            String storedFileName = boardEntity.getBoardFileEntityList().get(0).getStoredFileName();

            storedFileName = boardDTO.convertS3Url(storedFileName);
            boardDTO.setStoredFileName(storedFileName);
        }
        return boardDTO;
    }

    public String getFileName(String storedFileName) {
        String baseUrl = "https://www.mta.com.s3.ap-northeast-2.amazonaws.com/";
        if (storedFileName.startsWith(baseUrl)) {
            return storedFileName.substring(baseUrl.length());
        }
        return storedFileName;
    }

    private String convertS3Url(String storedFileName) {
        String region = "ap-northeast-2";
        String bucketName = "www.mta.com";
        String fileName= getFileName(storedFileName);
        return "https://s3." + region + ".amazonaws.com/" + bucketName + "/" + fileName;
    }
}
