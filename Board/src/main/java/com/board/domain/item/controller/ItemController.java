package com.board.domain.item.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.board.domain.comment.service.CommentService;
import com.board.domain.item.Item;
import com.board.domain.item.dto.ItemInfoDto;
import com.board.domain.item.dto.ItemSaveDto;
import com.board.domain.item.dto.LastPageDto;
import com.board.domain.item.service.ItemService;
import com.board.domain.member.dto.MemberInfoDto;
import com.board.domain.member.dto.MemberSessionDto;
import com.board.domain.member.service.MemberService;
import com.board.domain.post.dto.PostSaveDto;
import com.board.file.Item.dto.FileDto;
import com.board.file.Item.service.itemFileService;
import com.board.global.Login.MemberDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/shop/*")
@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

	private final ItemService itemService;
	private final itemFileService fileService;
	
	LastPageDto lastPage = new LastPageDto();
	
	@GetMapping("/get")
	public String getInfo(Model model,@RequestParam("id") Long id) {
		ItemInfoDto info = itemService.getInfo(id);
		List<FileDto> fileList = fileService.getFileList(id);
		model.addAttribute("item", info);
		model.addAttribute("fileList",fileList);
	    return "shop/get";
	}
	
	@GetMapping(value = "getImage/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> userSearch(@PathVariable("imagename") String imagename,@RequestParam("uploadDate") String uploadDate) throws IOException {
		String uploadDateStr = uploadDate.split("\\.")[0];
		log.info("uploadDate?? "+uploadDate);
		LocalDateTime date = LocalDateTime.parse(uploadDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
	    String formattedDate = date.format(DateTimeFormatter.ofPattern("yy/MM/dd"));
	    String result = formattedDate.replace("/", ""); 
	    log.info("이미지네임"+imagename);
		InputStream imageStream = new FileInputStream("D://SpringBootProject/upload/"+result+"/" + imagename);
		byte[] imageByteArray = IOUtils.toByteArray(imageStream);
		imageStream.close();
		return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public String getList(Model model, @RequestParam(value = "page",defaultValue = "0") int page,@RequestParam(name = "type", defaultValue = "all") String type,@RequestParam(name = "word", defaultValue = "") String word,@RequestParam(name = "selectSort", defaultValue = "createdDate") String selectSort,@RequestParam(name = "orderSort", defaultValue = "DESC") String orderSort) {
		PageRequest pageable=null;
		log.info("orderSort ?"+orderSort);
		if(orderSort.equals("ASC")) {
			pageable = PageRequest.of(0, 10, Sort.by(selectSort).ascending());
		}else {
			pageable = PageRequest.of(0, 10, Sort.by(selectSort).descending());			
		}
		Page<ItemInfoDto> itemList =itemService.getList(pageable, type, word);
		lastPage.recentPageSet(pageable.getPageNumber());

		int startpage = 1;
		if(itemList.getNumber()!=1) {
			startpage = itemList.getNumber()-itemList.getNumber()%10+1;
		}
		System.out.println("currentPAge : "+itemList.getNumber());
		int endpage = itemList.getNumber()+(10-itemList.getNumber()%10);
		if(endpage>itemList.getTotalPages()) {
			endpage = itemList.getTotalPages();
		}
		boolean nextPage = endpage%10==0?true:false;

	    model.addAttribute("itemList",itemList);
		model.addAttribute("prev",itemList.hasPrevious());
		model.addAttribute("next",nextPage);
		model.addAttribute("page",itemList.getNumber());
		model.addAttribute("startpage",startpage);
		model.addAttribute("endpage",endpage);
		
		return "shop/list";
	}
	
	@ResponseBody
	@PostMapping(value = "/add",consumes = "multipart/form-data")
	public ResponseEntity<String> add(ItemSaveDto board, @SessionAttribute("user") MemberSessionDto member,
			@RequestParam(value = "existIdx", required = false) List<Long> existIdx) {

//		if(category.equals("notice")) {
//			boolean hasAdminRole = memberDetailsService.checkAuthority("ROLE_ADMIN");
//			if(!hasAdminRole)
//				{log.error("관리자가 아닌 사용자가 공지사항 글쓰기 접근");
//					return ResponseEntity.ok("잘못된 요청입니다.");
//				}
//			}
		
		MemberInfoDto memberInfoDto = new MemberInfoDto();
		memberInfoDto.setDto(member);
		board.setSeller(memberInfoDto);
		Item item = itemService.saveItem(board,existIdx);

		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	
	
	@GetMapping("/write")
	public String writeGet(Model model,@ModelAttribute("vo") ItemSaveDto vo,Pageable pageable,@SessionAttribute("user") MemberSessionDto member) {
		log.info("PostController - writeGet method--------------------------------------------------");
		if (member == null) {
			log.info("로그인한 유저가 아님. redirect:/member/login ");
	        return "redirect:/member/login";
	    }
		
		
		MemberInfoDto memberinfoDto = new MemberInfoDto();
		memberinfoDto.setDto(member);
		vo.setSeller(memberinfoDto);
		model.addAttribute("vo", vo);

		List<FileDto> fileList = new ArrayList<>(); 
		model.addAttribute("fileList",fileList);
		return "shop/write";
	}

	
}
