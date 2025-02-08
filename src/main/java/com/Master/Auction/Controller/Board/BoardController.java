package com.Master.Auction.Controller.Board;

import com.Master.Auction.Service.Board.CommentService;
import com.Master.Auction.Service.Board.BoardService;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import com.Master.Auction.DTO.Board.CommentDTO;
import com.Master.Auction.DTO.Board.BoardDTO;
import org.springframework.data.domain.Page;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;
import java.util.List;

@RequestMapping("/Board")
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/list")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 10;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "Board/list";
    }

    @GetMapping("/save")
    public String save() {
        return "Board/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO, HttpSession session) throws IOException {
        Long id = (Long) session.getAttribute("loginId");
        boardService.save(boardDTO, id);
        return "home";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "Board/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/Board/list";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "Board/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, HttpSession session, Model model) throws IOException {
        Long memberId = (Long) session.getAttribute("loginId");
        BoardDTO board = boardService.update(boardDTO, memberId);
        model.addAttribute("board", board);
        return "Board/detail";
    }
}
