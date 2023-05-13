package com.board.file.Item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.file.Item.itemFile;

public interface itemFileRepository extends JpaRepository<itemFile, Long> {
	List<itemFile> findByItemId(Long id);
}
