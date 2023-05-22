package com.board.domain.address.service;

import com.board.domain.address.dto.addressDto;

public interface AddressService {
	public addressDto getAddress(String username);
	public addressDto setAddress(String username, addressDto dto);
}
