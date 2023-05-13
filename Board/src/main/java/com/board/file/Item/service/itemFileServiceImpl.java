package com.board.file.Item.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.board.domain.item.Item;
import com.board.domain.item.repository.ItemRepository;
import com.board.file.Item.itemFile;
import com.board.file.Item.dto.FileDto;
import com.board.file.Item.exception.FileException;
import com.board.file.Item.exception.FileExceptionType;
import com.board.file.Item.repository.itemFileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class itemFileServiceImpl implements itemFileService {
	
	private final itemFileRepository fileRepository;
	private final ItemRepository itemRepository;
	
	private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
	private final String uploadPath = Paths.get("D:","SpringBootProject","upload",today).toString();
	
	private final String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	@Override
public List<itemFile> save(MultipartFile[] multipartFile,Long boardIdx) {
	// TODO Auto-generated method stub
List<itemFile> attachList = new ArrayList<>();
		
		File dir = new File(uploadPath);
		if(dir.exists()==false) {
			dir.mkdirs();
		}
		
		for(MultipartFile file:multipartFile){
			if(file.getSize()<1) {
				continue;
			}
			try {
				final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				final String saveName = getRandomString() + '.' + extension;
				
				File target = new File(uploadPath,saveName);
				file.transferTo(target);
				itemFile attach = itemFile.builder().originalName(file.getOriginalFilename()).saveName(saveName).imageSize(file.getSize()).build();
				System.out.println(attach.getOriginalName()+attach.getSaveName());
				attachList.add(attach);
				System.out.println("파일파일처리"+boardIdx);
				Item item = itemRepository.findById(boardIdx).orElseThrow();
				//				attach.addFilese(postRepository.findByIdx(boardIdx).orElseThrow(()-> new FileException(FileExceptionType.FILE_CAN_NOT_SAVE)));
				attach.setItem(item);
				attach.setDeleteYn("N");
				attach.setFilePath(uploadPath);
				fileRepository.save(attach);
			} catch (IOException e) {
				throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);

			} 
		}
	return attachList;
}

	@Override
	public void delete(String filePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FileDto getFileDetails(Long idx) {
		// TODO Auto-generated method stub 
		return new FileDto(fileRepository.findById(idx).orElseThrow(()->new FileException(FileExceptionType.File_NOT_EXIST)));
	}

	@Override
	public List<FileDto> getFileList(Long id) {
		// TODO Auto-generated method stub
		List<FileDto> list = fileRepository.findByItemId(id).stream()
											.filter(file->"N".equals(file.getDeleteYn()))
											.map(file -> new FileDto(file))
											.collect(Collectors.toList())
											;
		
		return list;
	}

	@Override
	public void saveFile(FileDto fileDto) {
		// TODO Auto-generated method stub
		Item item = itemRepository.getById(fileDto.getBoardIdx());
		itemFile bf= fileDto.toEntity();
		bf.setItem(item);
		fileRepository.save(bf);
		
	}

}
