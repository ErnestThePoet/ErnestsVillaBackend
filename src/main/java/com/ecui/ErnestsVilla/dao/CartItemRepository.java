package com.ecui.ErnestsVilla.dao;

import com.ecui.ErnestsVilla.entity.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
}
