package com.board.domain.item.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.board.domain.item.Item;
import com.board.domain.item.dto.ItemInfoDto;

public interface ItemRepository extends JpaRepository<Item, Long>{

	@Query("SELECT i FROM Item i LEFT JOIN FETCH i.fileLists WHERE i.id=:id")
	Optional<Item> getInfo(@Param("id") Long id);
	
	Page<Item> findByItemnameAndIsSoldOut(String itemname, String isSoldOut, Pageable pageable);
	Page<Item> findBySellerAndIsSoldOut(Pageable pageable, String seller, String isSoldOut);
	Page<Item> findAllByIsSoldOut(Pageable pageable, String isSoldOut);
	}
