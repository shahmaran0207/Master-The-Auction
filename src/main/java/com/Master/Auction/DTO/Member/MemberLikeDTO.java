package com.Master.Auction.DTO.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@Getter
@Setter
@Data
public class MemberLikeDTO {

    private Long id;
    private Long memberId;
    private Long AnotherMemberId;
}
