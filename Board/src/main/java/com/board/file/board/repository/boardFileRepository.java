package com.board.file.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.file.board.boardFile;

public interface boardFileRepository extends JpaRepository<boardFile, Long> {
	List<boardFile> findByPostIdx(Long idx);
}
