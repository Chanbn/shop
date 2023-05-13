package com.board.file.Item;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.board.domain.BaseTimeEntity;
import com.board.domain.item.Item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
//@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "itemfile")
@Entity
public class itemFile extends BaseTimeEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_file_seq")
	@SequenceGenerator(name = "item_file_seq", sequenceName = "item_file_seq", allocationSize = 1)
	private Long idx;

//	/** 게시글 번호 (FK) */
//	private Long boardIdx;
 
	/** 원본 파일명 */
	private String originalName;

	/** 저장 파일명 */
	private String saveName;

	/** 파일 크기 */
	private long imageSize;
	@ColumnDefault("N")
	private String deleteYn;
	
	private String filePath;
	
	@Builder
	public itemFile(Long idx,String originalName,String saveName, long imageSize,String deleteYn,String filePath) {
	this.idx = idx;
	this.originalName = originalName;
	this.saveName = saveName;
	this.imageSize = imageSize;
	this.deleteYn=deleteYn;
	this.filePath = filePath;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_id")
	private Item item;
	
	public void setItem(Item item) {
		this.item = item;
	}
	
}
