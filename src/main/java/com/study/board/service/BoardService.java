package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 게시글 작성
    @Transactional
    public String boardWrite(Board board, MultipartFile file) throws Exception {

        if (file.getOriginalFilename().length() > 0) {
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

            UUID uuid = UUID.randomUUID();
            String fileguid = uuid.toString();
            String filename = file.getOriginalFilename();

            File saveFile = new File(projectPath, fileguid + "_" + filename);
            file.transferTo(saveFile);

            board.setFileguid(fileguid);
            board.setFilename(filename);
            board.setFilepath("/files/" + fileguid + "_" + filename);
        }

        if (board.getInsertdate() == null) {
            LocalDate nowDateTime = LocalDate.now();
            board.setInsertdate(nowDateTime);
        }

        boardRepository.save(board);


        return "success";
    }

    // 게시글 목록
    public Page<Board> boardList (Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    // 게시글 상세
    public Board boardView (Integer id) {
        return boardRepository.findById(id).get();
    }

    // 게시글 삭제
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }

    // 게시글 검색
    public Page<Board> boardSearchList (String searchKeyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }
}
