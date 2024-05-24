package org.example.board_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.board_project.domain.Board;
import org.example.board_project.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    //전체 조회
    @GetMapping("/list")
    public String showAllBoards(Model model,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page -1, size);

        Page<Board> boards =  boardService.findAllBoards(pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("currentPage", page);
        return "boards/List";
    }

    //단일 조회
    @GetMapping("/view/{id}")
    public String showBoard(@PathVariable Long id, Model model) {
        Board board = boardService.findBoardById(id);
        model.addAttribute("board" ,board);
        return "boards/View";
    }

    //글 작성 form 요청
    @GetMapping("/writeform")
    public String createForm(Model model) {
        model.addAttribute("board", new Board());
        return "boards/WriteForm";
    }

    //글 작성 등록 요청
    @PostMapping("/writeform")
    public String createBoard(@ModelAttribute Board board, RedirectAttributes redirectAttributes) {
        boardService.createBoard(board);
        redirectAttributes.addFlashAttribute("message", "게시글 작성 완료");
        return "redirect:/list";
    }

    //글 수정 form 요청
    @GetMapping("/updateform/{id}")
    public String modifyForm(@PathVariable Long id, Model model) {
        Board board = boardService.findBoardById(id);
        model.addAttribute("board", board);
        return "boards/UpdateForm";
    }

    //글 수정 등록 요청
    @PostMapping("/updateform")
    public String modifyBoard(@ModelAttribute Board board, Model model) {
        boolean isModified = boardService.modifyBoard(board);
        if (!isModified) {
            model.addAttribute("errorMsg", true);
            model.addAttribute("board", board);
            return "boards/UpdateForm";
        }
        return "redirect:/list";
    }

    //글 삭제 form 요청
    @GetMapping("/deleteform/{id}")
    public String deleteForm(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "boards/DeleteForm";
    }

    //글 삭제 등록 요청
    @PostMapping("/deleteform")
    public String deleteBoard(@RequestParam Long id, @RequestParam String password, Model model) {
        boolean isDeleted = boardService.deleteBoard(id, password);
        if (!isDeleted) {
            model.addAttribute("errorMsg", true);
            model.addAttribute("id", id);
            return "boards/DeleteForm";
        }
        return "redirect:/list";
    }

}
