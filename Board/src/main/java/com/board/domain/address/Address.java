package com.board.domain.address;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.board.domain.BaseTimeEntity;
import com.board.domain.address.dto.addressDto;
import com.board.domain.member.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "address")
public class Address extends BaseTimeEntity{
 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	private String name;
	private String address;
	private String hp;

	
	@Builder
	public Address(Member member, String name, String address,String hp) {
		this.member = member;
		this.name = name;
		this.address = address;
		this.hp = hp;
	}
	
	@Builder
	public Address(String name, String address, String hp) {
		this.name = name;
		this.address = address;
		this.hp = hp;		
	}  
	
	@Builder
	public Address(Member member) {
		this.member = member;
	}
	
	public void updateAddress(addressDto dto) {
		this.name = dto.getName();
		this.address = dto.getAddress();
		this.hp = dto.getHp();
	}
}
