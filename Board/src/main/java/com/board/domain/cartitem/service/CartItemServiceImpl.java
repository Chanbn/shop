package com.board.domain.cartitem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.cart.Cart;
import com.board.domain.cart.exception.CartException;
import com.board.domain.cart.exception.CartExceptionType;
import com.board.domain.cart.repository.CartRepository;
import com.board.domain.cartitem.CartItem;
import com.board.domain.cartitem.exception.CartItemException;
import com.board.domain.cartitem.exception.CartItemExceptionType;
import com.board.domain.cartitem.repository.CartItemRepository;
import com.board.domain.item.Item;
import com.board.domain.item.exception.ItemException;
import com.board.domain.item.exception.ItemExceptionType;
import com.board.domain.item.repository.ItemRepository;
import com.board.domain.member.Member;
import com.board.domain.member.dto.MemberSessionDto;
import com.board.domain.member.exception.MemberException;
import com.board.domain.member.exception.MemberExceptionType;
import com.board.domain.member.repository.MemberRepository;
import com.board.domain.post.repository.PostRepository;
import com.board.domain.post.service.PostServiceImpl;
import com.board.file.board.service.boardFileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartItemServiceImpl implements CartItemService {
	
	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	@Override
	public CartItem addItem(Long itemId, int count,MemberSessionDto memberDto) {
		// TODO Auto-generated method stub
		Item item = itemRepository.findById(itemId).orElseThrow(()->new ItemException(ItemExceptionType.WRONG_ITEM));
		Member member = memberRepository.findByUsername(memberDto.getUsername()).orElseThrow(()->new MemberException(MemberExceptionType.WRONG_USER));
		
		Cart cart = cartRepository.findByMemberUsername(member.getUsername()).orElseGet(()->{
																Cart newCart = Cart.createCart(member);
																cartRepository.save(newCart);
																return newCart;});
		CartItem savedItem = cartItemRepository.findByItemIdAndCartId(itemId,cart.getId());
		if(savedItem!=null) {
			savedItem.addCount(count);
			return savedItem;
		}
		CartItem cartitem = CartItem.builder().cart(cart).item(item).count(count).build();			
		
		return cartItemRepository.save(cartitem);
	}

	@Override
	public CartItem updateItem(Long id, int count,MemberSessionDto memberDto) {
		// TODO Auto-generated method stub
		CartItem item = cartItemRepository.findById(id).orElseThrow(()->new CartItemException(CartItemExceptionType.WRONG_CARTITEM));
		item.setCount(count);
		Cart cart = cartRepository.findByMemberUsername(memberDto.getUsername()).orElseThrow(()->new CartException(CartExceptionType.WRONG_CART));
		cart.addItem(item);
		return cartItemRepository.save(item);
	}

	@Override
	public void removeItem(Long id) {
		// TODO Auto-generated method stub
		CartItem item = cartItemRepository.findById(id).orElseThrow(()->new CartItemException(CartItemExceptionType.WRONG_CARTITEM));
		cartItemRepository.deleteById(id);
	}

	@Override
	public void removeAllCartItem(MemberSessionDto memberDto) {
		// TODO Auto-generated method stub
		Cart cart = cartRepository.findByMemberUsername(memberDto.getUsername()).orElseThrow(()->new CartException(CartExceptionType.WRONG_CART));
		cart.getWishList().forEach(cartItem->removeItem(cartItem.getId()));
	}
	
	

}
