package com.board.file.Item.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.board.file.Item.itemFile;
import com.board.file.Item.dto.FileDto;

public interface itemFileService {
	List<itemFile> save(MultipartFile[] multipartFile,Long boardIdx);
	void delete(String filePath);
	FileDto getFileDetails(Long idx);
	List<FileDto> getFileList(Long id);
	void saveFile(FileDto fileDto);
}
