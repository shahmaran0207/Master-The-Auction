package com.Master.Auction.Entity.Board;

import com.Master.Auction.Entity.Member.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "board_hate_table")
public class BoardHateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private BoardEntity boardEntity;

    public static BoardHateEntity toSaveEntity(MemberEntity member, BoardEntity board) {
        BoardHateEntity boardHateEntity = new BoardHateEntity();
        boardHateEntity.setMemberEntity(member);
        boardHateEntity.setBoardEntity(board);
        return boardHateEntity;
    }
}
