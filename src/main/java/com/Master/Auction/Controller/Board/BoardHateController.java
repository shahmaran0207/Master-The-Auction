package com.Master.Auction.Controller.Board;

import com.Master.Auction.Service.Board.BoardHateService;
import org.springframework.web.bind.annotation.*;
import com.Master.Auction.DTO.Board.BoardHateDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/board_hates")
@RequiredArgsConstructor
public class BoardHateController {

    private final BoardHateService boardHateService;

    @PostMapping("/toggle")
    public String toggleHate(@RequestBody BoardHateDTO boardHateDTO) {
        return boardHateService.toggleHate(boardHateDTO);
    }

    @GetMapping("/count/{boardId}")
    public int getHateCount(@PathVariable Long boardId) {
        return boardHateService.getHateCount(boardId);
    }

    @GetMapping("/status/{boardId}/{memberId}")
    public boolean checkHateStatus(@PathVariable Long boardId, @PathVariable Long memberId) {
        return boardHateService.isHatedByMember(boardId, memberId);
    }

}

