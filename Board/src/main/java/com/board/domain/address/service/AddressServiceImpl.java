package com.board.domain.address.service;

import org.springframework.stereotype.Service;

import com.board.domain.address.Address;
import com.board.domain.address.dto.addressDto;
import com.board.domain.address.repository.AddressRepository;
import com.board.domain.member.Member;
import com.board.domain.member.exception.MemberException;
import com.board.domain.member.exception.MemberExceptionType;
import com.board.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

	private final AddressRepository addressRepository;
	private final MemberRepository memberRepository;
	
	@Override
	public addressDto getAddress(String username) {
		// TODO Auto-generated method stub
		Member member = memberRepository.findByUsername(username).orElseThrow(()->new MemberException(MemberExceptionType.WRONG_USER));
		Long userId = member.getId();
		addressDto dto = new addressDto(addressRepository.findByMemberId(userId).orElseGet(() -> {
		Address newAddressDto = Address.builder().member(member).address("").hp("").name("").build();
		return newAddressDto;
		}));
		
		return dto;
	}

	@Override
	public addressDto setAddress(String username, addressDto dto) {
		// TODO Auto-generated method stub
		Member member = memberRepository.findByUsername(username).orElseThrow(()->new MemberException(MemberExceptionType.WRONG_USER));
		Long userId = member.getId();
		Address address = addressRepository.findByMemberId(userId).orElseGet(()->{
			return null;			
		});
		
		address.updateAddress(dto);
		return new addressDto(addressRepository.save(address));

	}

}
