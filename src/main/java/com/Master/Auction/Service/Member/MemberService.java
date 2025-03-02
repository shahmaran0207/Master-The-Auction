package com.Master.Auction.Service.Member;

import com.Master.Auction.Repository.Member.MemberProfileRepository;
import com.Master.Auction.Repository.Member.MemberRepository;
import com.Master.Auction.Entity.Member.MemberProfileEntity;
import org.springframework.web.multipart.MultipartFile;
import com.google.firebase.auth.FirebaseAuthException;
import com.Master.Auction.Entity.Member.MemberEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.Master.Auction.DTO.Member.MemberDTO;
import org.springframework.stereotype.Service;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;
import java.util.List;
import java.io.File;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;

    public String emailCheck(String memberemail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMail(memberemail);
        if(optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            return memberEntity.getMail();
        } else return null;
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public void save(MemberDTO memberDTO) throws IOException, FirebaseAuthException {

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(memberDTO.getMail())
                .setPassword(memberDTO.getMemberPassword());
        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        if (memberDTO.getMemberProfile() == null) {
            MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
            memberRepository.save(memberEntity);

        } else {
            MultipartFile memberProfile = memberDTO.getMemberProfile();
            String originalFilename = memberProfile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Master-The-Auction/profile/" + storedFileName;
            memberProfile.transferTo(new File(savePath));

            MemberEntity memberEntity = MemberEntity.toSaveMemberFile(memberDTO);
            Long savedId = memberRepository.save(memberEntity).getId();
            MemberEntity savedBoardEntity = memberRepository.findById(savedId).get();

            MemberProfileEntity memberProfileEntity = MemberProfileEntity.toMemberProfileEntity(savedBoardEntity, originalFilename, storedFileName);
            memberProfileRepository.save(memberProfileEntity);
        }
    }

    public MemberDTO login(String email) {
        Optional<MemberEntity> bymemberemail = memberRepository.findByMail(email);

        MemberEntity memberEntity = bymemberemail.get();
        MemberDTO dto= MemberDTO.toMemberDTO(memberEntity);

        return dto;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);

        if(optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else return null;
    }

    public Page<MemberDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<MemberEntity> memberEntities =
                memberRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<MemberDTO> memberDTOS = memberEntities.map(member ->
                new MemberDTO(member.getId(), member.getMail(),member.getMemberName(), member.getBirthday(),
                        member.getLikesCount(), member.getHatesCount(), member.getMoney()));
        return memberDTOS;
    }

    public MemberDTO update(MemberDTO memberDTO) throws IOException, FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(memberDTO.getMail())
                .setPassword(memberDTO.getMemberPassword());
        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

        MemberEntity exisitingMemberEntity = memberRepository.findById(memberDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberDTO.getId()));

        if (!memberDTO.getMemberProfile().isEmpty()) {
            List<MemberProfileEntity> existingProfiles = memberProfileRepository.findByMemberEntity(exisitingMemberEntity);

            for (MemberProfileEntity profile : existingProfiles) {
                String oldFilePath = "C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Master-The-Auction/profile/" + profile.getStoredFileName();
                File oldFile = new File(oldFilePath);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
                memberProfileRepository.delete(profile);
            }

            MultipartFile memberProfile = memberDTO.getMemberProfile();
            String originalFilename = memberProfile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Master-The-Auction/profile/" + storedFileName;
            memberProfile.transferTo(new File(savePath));

            MemberProfileEntity memberProfileEntity = MemberProfileEntity.toMemberProfileEntity(exisitingMemberEntity, originalFilename, storedFileName);
            memberProfileRepository.save(memberProfileEntity);
        }

        if (!memberDTO.getMemberName().isEmpty()) {
            exisitingMemberEntity.setMemberName(memberDTO.getMemberName());
        }

        exisitingMemberEntity.setBirthday(exisitingMemberEntity.getBirthday());
        exisitingMemberEntity.setHatesCount(exisitingMemberEntity.getHatesCount());
        exisitingMemberEntity.setLikesCount(exisitingMemberEntity.getLikesCount());
        exisitingMemberEntity.setMail(exisitingMemberEntity.getMail());
        exisitingMemberEntity.setRole(exisitingMemberEntity.getRole());
        exisitingMemberEntity.setMoney(exisitingMemberEntity.getMoney());

        memberRepository.save(exisitingMemberEntity);
        return findById(memberDTO.getId());
    }

    public void buyPoint(Long memberId, MemberDTO memberDTO) {

        MemberEntity exisitingMemberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberDTO.getId()));

        exisitingMemberEntity.setMoney(exisitingMemberEntity.getMoney()+ memberDTO.getMoney());
        memberRepository.save(exisitingMemberEntity);
    }
}
