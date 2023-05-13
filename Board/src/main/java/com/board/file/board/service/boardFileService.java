package com.board.file.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.board.file.board.boardFile;
import com.board.file.board.dto.FileDto;

public interface boardFileService {
	List<boardFile> save(MultipartFile[] multipartFile,Long boardIdx);
	void delete(String filePath);
	FileDto getFileDetails(Long idx);
	List<FileDto> getFileList(Long idx);
	void saveFile(FileDto fileDto);
}
