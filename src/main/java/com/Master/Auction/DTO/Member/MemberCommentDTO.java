package com.Master.Auction.DTO.Member;

import java.time.LocalDateTime;

import com.Master.Auction.Entity.Member.MemberCommentEntity;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class MemberCommentDTO {

    private Long id;
    private Long commentTargetId;
    private Long memberId;

    private String commentWriter;
    private String commentContents;

    private LocalDateTime commentCreatedTime;

    public static MemberCommentDTO toCommentDTO(MemberCommentEntity commentEntity, Long commentTargetId) {
        MemberCommentDTO commentDTO = new MemberCommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentCreatedTime(commentEntity.getCommentDate());
        commentDTO.setCommentTargetId(commentTargetId);
        return commentDTO;
    }
}
