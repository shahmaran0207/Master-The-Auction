package com.Master.Auction.DTO.Member;

import org.springframework.web.multipart.MultipartFile;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long id;

    private int fileAttached;
    private int role;

    private String MemberPassword;
    private String memberEmail;
    private String memberName;
    private String originalFileName;
    private String storedFileName;

    private String birthday;

    private MultipartFile memberProfile;
}