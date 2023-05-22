package com.board.domain.address.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.domain.address.Address;
import com.board.domain.address.dto.addressDto;

public interface AddressRepository extends JpaRepository<Address, Long>{
	Optional<Address> findByMemberId(Long id); 
//	Optional<addressDto> findByMemberIdAndIsDefault(Long id, String isDefault);
}
