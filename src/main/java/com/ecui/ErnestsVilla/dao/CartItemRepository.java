package com.ecui.ErnestsVilla.dao;

import com.ecui.ErnestsVilla.entity.CartItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
    boolean existsByIdAndCustomerAccount(Integer id, String customerAccount);
    boolean existsByItemIdAndCustomerAccount(Integer itemId, String customerAccount);

    Optional<CartItem> findByIdAndCustomerAccount(Integer id, String customerAccount);

    List<CartItem> findByCustomerAccount(String customerAccount);

    @Modifying
    @Transactional
    void deleteByCustomerAccount(String customerAccount);
}
