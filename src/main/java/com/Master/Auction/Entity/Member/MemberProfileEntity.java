package com.Master.Auction.Entity.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="member_profile_table")
public class MemberProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static MemberProfileEntity toMemberProfileEntity(MemberEntity memberEntity, String originalFileName, String storedFileName) {
        MemberProfileEntity memberProfileEntity = new MemberProfileEntity();
        memberProfileEntity.setOriginalFileName(originalFileName);
        memberProfileEntity.setStoredFileName(storedFileName);
        memberProfileEntity.setMemberEntity(memberEntity);
        return memberProfileEntity;
    }

}