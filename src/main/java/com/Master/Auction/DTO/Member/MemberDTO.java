package com.Master.Auction.DTO.Member;

import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.Master.Auction.Entity.Member.MemberEntity;
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
    private int likesCount;
    private int hatesCount;

    @JsonProperty("Money")
    private int Money;

    private String MemberPassword;
    private String ChangeMemberPassword;
    private String mail;
    private String memberName;
    private String originalFileName;
    private String changeFileName;
    private String storedFileName;

    private String birthday;

    private MultipartFile memberProfile;

    public MemberDTO(Long id, String mail, String memberName, String birthday, int likesCount, int hatesCount, int Money){
        this.id = id;
        this.mail = mail;
        this.memberName = memberName;
        this.birthday = birthday;
        this.likesCount = likesCount;
        this.hatesCount = hatesCount;
        this.Money = Money;
    }

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setRole(memberEntity.getRole());
        memberDTO.setMail(memberEntity.getMail());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMoney(memberEntity.getMoney());

        if (memberEntity.getFileAttached() == 0) {
            memberDTO.setFileAttached(memberEntity.getFileAttached());
        } else {
            memberDTO.setFileAttached(memberEntity.getFileAttached());
            memberDTO.setOriginalFileName(memberEntity.getMemberProfileEntityList().get(0).getOriginalFileName());

            String storedFileName = memberEntity.getMemberProfileEntityList().get(0).getStoredFileName();

            storedFileName = memberDTO.convertS3Url(storedFileName);
            memberDTO.setStoredFileName(storedFileName);
        }
        return memberDTO;
    }

    public String getFileName(String storedFileName) {
        String baseUrl = "https://www.mta.com.s3.ap-northeast-2.amazonaws.com/";
        if (storedFileName.startsWith(baseUrl)) {
            return storedFileName.substring(baseUrl.length());
        }
        return storedFileName;
    }

    private String convertS3Url(String storedFileName) {
        String region = "ap-northeast-2";
        String bucketName = "www.mta.com";
        String fileName= getFileName(storedFileName);
        return "https://s3." + region + ".amazonaws.com/" + bucketName + "/" + fileName;
    }
}