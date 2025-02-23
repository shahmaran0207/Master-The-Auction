package com.Master.Auction.Entity.Member;

import com.Master.Auction.DTO.Member.MemberDTO;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
    private String mail;

    @Column(nullable = false)
    private String birthday;

    @Column
    private int role;

    @Column
    private int fileAttached;

    @Column
    private int likesCount = 0;

    @Column
    private int hatesCount = 0;

    @Column
    private int Money = 0;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberProfileEntity> memberProfileEntityList = new ArrayList<>();

    // 내가 좋아요를 누른 회원들
    @OneToMany(mappedBy = "liker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberLikeEntity> likedMembers = new ArrayList<>();

    // 나를 좋아요한 회원들
    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberLikeEntity> likedByMembers = new ArrayList<>();

    // 내가 좋아요를 누른 회원들
    @OneToMany(mappedBy = "hater", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberHateEntity> hatedMembers = new ArrayList<>();

    @OneToMany(mappedBy = "targetHater", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberHateEntity> hatedByMembers = new ArrayList<>();


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

    public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
        MemberEntity member = new MemberEntity();
        member.setMail(memberDTO.getMail());
        member.setMemberName(memberDTO.getMemberName());
        member.setBirthday(memberDTO.getBirthday());
        member.setRole(1);
        member.setHatesCount(0);
        member.setLikesCount(0);
        member.setMoney(0);
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
        memberEntity.setMail(memberDTO.getMail());
        memberEntity.setHatesCount(0);
        memberEntity.setMoney(0);
        memberEntity.setLikesCount(0);
        memberEntity.setFileAttached(1);
        return memberEntity;
    }
}
