package com.Master.Auction.DTO.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@Getter
@Setter
@Data
public class MemberHateDTO {

    private Long id;
    private Long hater;
    private Long target;
}
