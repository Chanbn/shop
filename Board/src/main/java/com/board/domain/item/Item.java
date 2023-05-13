package com.board.domain.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.board.domain.BaseTimeEntity;
import com.board.domain.member.Member;
import com.board.file.Item.itemFile;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Item extends BaseTimeEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private Long price;
	private Long stock;
	private String isSoldOut;
	private String detail;
	
	@Builder
	public Item(String name, Long price, Long stock, String detail) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.detail = detail;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id")
	private Member seller;

	@OneToMany(mappedBy = "item",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<itemFile> fileLists = new ArrayList<>();
	
	public void addFile(itemFile files) {
		fileLists.add(files);
		files.setItem(this);
	}
	
	public void update(String name, Long price, Long stock, String detail) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.detail = detail;
	}
	public void confirmSeller(Member seller) {
		this.seller = seller;
	}
	public void setSeller(Member seller) {
		this.seller = seller;
	}
}
