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
import com.Master.Auction.Service.ImageService;
import org.springframework.stereotype.Service;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ImageService imageService;
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
        if (memberDTO.getMemberProfile().isEmpty()) {
            MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
            memberRepository.save(memberEntity);

        } else {
            MultipartFile memberProfile = memberDTO.getMemberProfile();

            String s3Url = imageService.imageUploadFromFile(memberProfile);

            String originalFilename = memberProfile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

            MemberEntity memberEntity = MemberEntity.toSaveMemberFile(memberDTO);
            Long savedId = memberRepository.save(memberEntity).getId();
            MemberEntity savedBoardEntity = memberRepository.findById(savedId).get();

            MemberProfileEntity memberProfileEntity = MemberProfileEntity.toMemberProfileEntity(savedBoardEntity, storedFileName, s3Url);
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

        MemberEntity existingMemberEntity = memberRepository.findById(memberDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberDTO.getId()));

        if (!memberDTO.getMemberProfile().isEmpty()) {
            List<MemberProfileEntity> existingProfiles = memberProfileRepository.findByMemberEntity(existingMemberEntity);

            for (MemberProfileEntity profile : existingProfiles) {
                // S3에서 기존 파일 삭제 (삭제 로직 필요 시 구현)
                imageService.deleteImage(profile.getStoredFileName());
                memberProfileRepository.delete(profile);
            }

            MultipartFile memberProfile = memberDTO.getMemberProfile();
            String s3Url = imageService.imageUploadFromFile(memberProfile);

            String originalFilename = memberProfile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

            MemberProfileEntity memberProfileEntity = MemberProfileEntity.toMemberProfileEntity(existingMemberEntity, storedFileName, s3Url);
            memberProfileRepository.save(memberProfileEntity);
        }

        if (!memberDTO.getMemberName().isEmpty()) {
            existingMemberEntity.setMemberName(memberDTO.getMemberName());
        }

        existingMemberEntity.setBirthday(existingMemberEntity.getBirthday());
        existingMemberEntity.setHatesCount(existingMemberEntity.getHatesCount());
        existingMemberEntity.setLikesCount(existingMemberEntity.getLikesCount());
        existingMemberEntity.setMail(existingMemberEntity.getMail());
        existingMemberEntity.setRole(existingMemberEntity.getRole());
        existingMemberEntity.setMoney(existingMemberEntity.getMoney());

        memberRepository.save(existingMemberEntity);
        return findById(memberDTO.getId());
    }

    public void buyPoint(Long memberId, MemberDTO memberDTO) {

        MemberEntity exisitingMemberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberDTO.getId()));

        exisitingMemberEntity.setMoney(exisitingMemberEntity.getMoney()+ memberDTO.getMoney());
        memberRepository.save(exisitingMemberEntity);
    }
}
