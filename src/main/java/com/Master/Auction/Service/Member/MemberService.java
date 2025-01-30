package com.Master.Auction.Service.Member;

import com.Master.Auction.Repository.Member.MemberProfileRepository;
import com.Master.Auction.Repository.Member.MemberRepository;
import com.Master.Auction.Entity.Member.MemberProfileEntity;
import org.springframework.web.multipart.MultipartFile;
import com.google.firebase.auth.FirebaseAuthException;
import com.Master.Auction.Entity.Member.MemberEntity;
import com.Master.Auction.Config.PasswordUtils;
import com.Master.Auction.DTO.Member.MemberDTO;
import org.springframework.stereotype.Service;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;
import java.io.File;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordUtils passwordUtils;
    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;

    public String emailCheck(String memberemail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberemail);
        if(optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            return memberEntity.getMemberEmail();
        } else return null;
    }


    public void save(MemberDTO memberDTO) throws IOException, FirebaseAuthException {
        String encodedPassword = PasswordUtils.encodePassword(memberDTO.getMemberPassword());

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(memberDTO.getMemberEmail())
                .setPassword(encodedPassword);

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

        System.out.println(memberDTO);
        if (memberDTO.getMemberProfile() == null) {
            MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
            memberRepository.save(memberEntity);

        } else {
            MultipartFile memberProfile = memberDTO.getMemberProfile();
            String originalFilename = memberProfile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Member_project/profile/" + storedFileName;
            memberProfile.transferTo(new File(savePath));

            MemberEntity memberEntity = MemberEntity.toSaveMemberFile(memberDTO);
            Long savedId = memberRepository.save(memberEntity).getId();
            MemberEntity savedBoardEntity = memberRepository.findById(savedId).get();

            MemberProfileEntity memberProfileEntity = MemberProfileEntity.toMemberProfileEntity(savedBoardEntity, originalFilename, storedFileName);
            memberProfileRepository.save(memberProfileEntity);
        }
    }

}
