package com.Master.Auction.Entity.Member;

import com.Master.Auction.DTO.Member.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="member_table")
@Entity
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String MemberName;

    @Column(nullable = false)
    private String memberEmail;

    @Column(nullable = false)
    private String birthday;

    @Column
    private int role;

    @Column
    private int fileAttached;

    public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
        MemberEntity member = new MemberEntity();
        member.setMemberEmail(memberDTO.getMemberEmail());
        member.setMemberName(memberDTO.getMemberName());
        member.setBirthday(memberDTO.getBirthday());
        member.setRole(1);
        member.setId(memberDTO.getId());
        member.setFileAttached(0);
        return member;
    }

    public static MemberEntity toSaveMemberFile(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setRole(memberDTO.getRole());
        memberEntity.setBirthday(memberDTO.getBirthday());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setFileAttached(1);
        return memberEntity;
    }
}
