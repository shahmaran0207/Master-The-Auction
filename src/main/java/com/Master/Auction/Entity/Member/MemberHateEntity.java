package com.Master.Auction.Entity.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Member_Hate_Table")
public class MemberHateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hater_id", nullable = false)
    private MemberEntity hater;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetHater", nullable = false)
    private MemberEntity targetHater;

    public static MemberHateEntity toSaveEntity(MemberEntity hater, MemberEntity target) {
        MemberHateEntity memberHateEntity = new MemberHateEntity();
        memberHateEntity.setHater(hater);
        memberHateEntity.setTargetHater(target);
        return memberHateEntity;
    }
}
