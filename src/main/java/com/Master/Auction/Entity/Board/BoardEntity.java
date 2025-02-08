package com.Master.Auction.Entity.Board;

import com.Master.Auction.Entity.Member.MemberEntity;
import com.Master.Auction.DTO.Board.BoardDTO;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="board_table")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    @Column
    private int fileAttached;

    @Column
    private int likesCount = 0;

    @Column
    private int hatesCount = 0;

    @Column
    private LocalDateTime boardCreatedTime;

    @Column
    private LocalDateTime boardUpdatedTime;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    public void increaseLikesCount() {
        this.likesCount++;
    }

    public void decreaseLikesCount() {
        if (this.likesCount > 0) {
            this.likesCount--;
        }
    }

    public void increaseHatesCount() {
        this.hatesCount++;
    }

    public void decreaseHatesCount() {
        if (this.hatesCount > 0) {
            this.hatesCount--;
        }
    }

    public static BoardEntity toSaveEntity(BoardDTO boardDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardCreatedTime(LocalDateTime.now());
        boardEntity.setLikesCount(0);
        boardEntity.setHatesCount(0);
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(0);
        boardEntity.setMemberEntity(memberEntity);
        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardCreatedTime(LocalDateTime.now());
        boardEntity.setLikesCount(0);
        boardEntity.setHatesCount(0);
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(1);
        boardEntity.setMemberEntity(memberEntity);
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setLikesCount(boardDTO.getLikesCount());
        boardEntity.setHatesCount(boardDTO.getHatesCount());
        boardEntity.setMemberEntity(memberEntity);
        return boardEntity;
    }

    public static BoardEntity toUpdateFileEntity(BoardDTO boardDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardUpdatedTime(LocalDateTime.now());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setLikesCount(boardDTO.getLikesCount());
        boardEntity.setHatesCount(boardDTO.getHatesCount());
        boardEntity.setFileAttached(1);
        boardEntity.setMemberEntity(memberEntity);
        return boardEntity;
    }
}
