package com.board;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import com.board.domain.member.Member;
import com.board.domain.member.dto.MemberInfoDto;
import com.board.domain.member.repository.MemberRepository;
import com.board.domain.post.Post;
import com.board.domain.post.dto.PostSaveDto;
import com.board.domain.post.repository.PostRepository;
import com.board.domain.post.service.PostServiceImpl;
import com.board.file.board.boardFile;
import com.board.file.board.dto.FileDto;
import com.board.file.board.service.boardFileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class postServiceImplTest {

	@InjectMocks
	private PostServiceImpl postService;
	
	@Mock
	private PostRepository postRepository;
	
	@Mock
	private boardFileService fileService;
	
	@Mock
	private MemberRepository memberRepository;
	
	Post post;
	Member member;
	boardFile boardFile;
	boardFile boardFile2;
	MultipartFile mockFile1; 
	MultipartFile mockFile2;
	byte[] fileContent1;
	byte[] fileContent2;
	@BeforeEach
	public void setup() {
		postService = new PostServiceImpl(postRepository, memberRepository, fileService);
		
		fileContent1 = "Sample file content 1".getBytes();
		mockFile1 = new MockMultipartFile("file", "filename1.txt", "text/plain", fileContent1);

		fileContent2 = "Sample file content 2".getBytes();
		mockFile2 = new MockMultipartFile("file", "filename2.txt", "text/plain", fileContent2);

		
		post.builder()
			.content("내용")
			.title("제목")
			.category("자유")
			.build();
		post.setIdx(1L);
		  List<String> roles = new ArrayList<>();
		    roles.add("USER");
		     member = Member.builder()
		            .username("testId123")
		            .name("이름")
		            .email("test123@email.com")
		            .password("!qlalfqjsgh12")
		            .nickname("nick")
		            .roles(roles)
		            .delete_yn("N")
		            .build();
		     
		     boardFile.setDeleteYn("N");
		     boardFile.setIdx(1L);
		     boardFile.setOriginalName("originalName");
		     boardFile.setPost(post);
		     boardFile.setSaveName("savedName");
		     boardFile.setImageSize(314L);

		     boardFile2.setDeleteYn("N");
		     boardFile2.setIdx(2L);
		     boardFile2.setOriginalName("originalName2");
		     boardFile2.setPost(post);
		     boardFile2.setSaveName("savedName2");
		     boardFile2.setImageSize(315L);
		     
		     List<boardFile> fileList = new ArrayList<>();
		     fileList.add(boardFile);
		     fileList.add(boardFile2);
		     post.setFileLists(fileList);

	}
	
	@Nested
	class 저장{
		public void 성공() {
			
			FileDto fileDto = new FileDto(boardFile);
			FileDto fileDto2 = new FileDto(boardFile2);
			
			PostSaveDto postSaveDto = new PostSaveDto(post.getTitle(), post.getContent(),post.getCategory());
			MemberInfoDto memberInfoDto = new MemberInfoDto(member);
			postSaveDto.setWriter(memberInfoDto);
			postSaveDto.setFiles(null);
			when(memberRepository.findByUsername(postSaveDto.getWriter().getUsername())).thenReturn(Optional.of(member));
			when(postRepository.save(post)).thenReturn(post);
			
			List<FileDto> fileList = new ArrayList<>();
			fileList.add(fileDto);
			fileList.add(fileDto2);
			
			when(fileService.getFileList(post.getIdx())).thenReturn(fileList);
			
			when(fileService.getFileDetails(fileDto.getIdx())).thenReturn(fileDto);
			when(fileService.getFileDetails(fileDto2.getIdx())).thenReturn(fileDto2);
			
			when(fileService.save(null, null));
			
		}
	}

}
