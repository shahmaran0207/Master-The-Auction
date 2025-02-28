package com.Master.Auction.Service.Member;

import com.Master.Auction.DTO.Board.BoardDTO;
import com.Master.Auction.Entity.Board.BoardEntity;
import com.Master.Auction.Entity.Board.BoardFileEntity;
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

//        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
//                .setEmail(memberDTO.getMail())
//                .setPassword(memberDTO.getMemberPassword());
//        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
//
        MemberEntity exisitingMemberEntity = memberRepository.findById(memberDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberDTO.getId()));

        if (!memberDTO.getMemberName().isEmpty()) {
            exisitingMemberEntity.setMemberName(memberDTO.getMemberName());
            System.out.println("이름 있음"+memberDTO.getMemberName());
        }
        if (!memberDTO.getMemberProfile().isEmpty()) {
            System.out.println("사진 없음"+memberDTO.getMemberProfile().getOriginalFilename());
            MultipartFile memberProfile = memberDTO.getMemberProfile();
            String originalFilename = memberProfile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Master-The-Auction/profile/" + storedFileName;
            memberProfile.transferTo(new File(savePath));
        }

        System.out.println("이름+사진 없음"+memberDTO.getMemberName());
        System.out.println(exisitingMemberEntity.getBirthday());
//        exisitingMemberEntity.setId(exisitingMemberEntity.getId());
//        exisitingMemberEntity.setBirthday(memberDTO.getBirthday());

        //여긴 나머지 이름, 사지 ㄴ제외 정보들 DB에서 보고 입력
//            exisitingMemberEntity.setBoardPass(boardDTO.getBoardPass());
//            exisitingMemberEntity.setBoardTitle(boardDTO.getBoardTitle());
//            exisitingMemberEntity.setBoardContents(boardDTO.getBoardContents());
//            exisitingMemberEntity.setBoardHits(boardDTO.getBoardHits());
//            exisitingMemberEntity.setLikesCount(boardDTO.getLikesCount());
//            exisitingMemberEntity.setHatesCount(boardDTO.getHatesCount());
//            exisitingMemberEntity.setMemberEntity(memberEntity);

//
//            // 파일 정보 업데이트
//            existingBoardEntity.setBoardPass(boardDTO.getBoardPass());
//            existingBoardEntity.setFileAttached(1);
//            existingBoardEntity.setBoardTitle(boardDTO.getBoardTitle());
//            existingBoardEntity.setBoardContents(boardDTO.getBoardContents());
//            existingBoardEntity.setBoardHits(boardDTO.getBoardHits());
//            existingBoardEntity.setLikesCount(boardDTO.getLikesCount());
//            existingBoardEntity.setHatesCount(boardDTO.getHatesCount());
//            existingBoardEntity.setMemberEntity(memberEntity);
//
//            // 파일 엔티티 생성 및 저장
//            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(existingBoardEntity, originalFilename, storedFileName);
//            boardFileRepository.save(boardFileEntity);
//        }
//
        memberRepository.save(exisitingMemberEntity);
        return findById(memberDTO.getId());
    }
}
