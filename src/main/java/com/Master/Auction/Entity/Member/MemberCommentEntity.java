package com.Master.Auction.Entity.Member;

import java.time.LocalDateTime;

import com.Master.Auction.DTO.Member.MemberCommentDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member_comment_table")
public class MemberCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenterid")
    private MemberEntity commenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentTargetId")
    private MemberEntity commentTarget;

    @Column
    private LocalDateTime commentDate;

    public static MemberCommentEntity toSaveEntity(MemberCommentDTO commentDTO, MemberEntity target, MemberEntity memberEntity) {
        MemberCommentEntity commentEntity = new MemberCommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setCommentTarget(target);
        commentEntity.setCommenter(memberEntity);
        return commentEntity;
    }
}
