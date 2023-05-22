package com.board.domain.address.dto;

import com.board.domain.address.Address;
import com.board.domain.member.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class addressDto {
	private String name;
	private String address;
	private String hp;
	
	
	public addressDto(Address deliveryAddress) {
		this.name = deliveryAddress.getName();
		this.address = deliveryAddress.getAddress();
		this.hp = deliveryAddress.getHp();
	}
	
	public addressDto(String name, String address, String hp) {
		this.name = name;
		this.address = address;
		this.hp = hp;
	}

	public addressDto(addressDto addressData) {
		// TODO Auto-generated constructor stub
		this.name = addressData.getName();
		this.address = addressData.getAddress();
		this.hp = addressData.getHp();
	}
	

}
