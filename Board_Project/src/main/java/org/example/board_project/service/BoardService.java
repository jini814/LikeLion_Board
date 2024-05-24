package org.example.board_project.service;

import lombok.RequiredArgsConstructor;
import org.example.board_project.domain.Board;
import org.example.board_project.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    //전체 조회
    @Transactional(readOnly = true)
    public Iterable<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    public Page<Board> findAllBoards(Pageable pageable){
        Pageable sortedByDescId =  PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC,"id"));

        return boardRepository.findAll(sortedByDescId);
    }

    //글 단일 조회
    @Transactional(readOnly = true)
    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    //글 작성 요청
    @Transactional
    public Board createBoard(Board board) {
        board.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        return boardRepository.save(board);
    }

    //글 수정 요청
    @Transactional
    public boolean modifyBoard(Board board) {
        String pwd = boardRepository.findById(board.getId()).orElse(null).getPassword();

        if(pwd.equals(board.getPassword())) {
            board.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
            boardRepository.save(board);
            return true;
        } else {
        }   return false;
    }

    //글 삭제 요청
    @Transactional
    public boolean deleteBoard(Long id, String password) {
        Board board = boardRepository.findById(id).orElse(null);
        if (board != null && board.getPassword().equals(password)) {
            boardRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
