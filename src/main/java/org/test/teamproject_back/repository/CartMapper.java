package org.test.teamproject_back.repository;

import org.apache.ibatis.annotations.Mapper;
import org.test.teamproject_back.entity.Cart;

import java.util.List;

@Mapper
public interface CartMapper {
    int addCart(Cart cart);
    Long findCartIdByUserId(Long userId);
    List<Long> findCartItemIdByCartId(Long cartId);
    List<Cart> findCartListByUserId(Long userId);
    List<Cart> findCartListByCartItemId(Long cartItemId);
}
