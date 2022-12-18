package com.ecui.ErnestsVilla.dao;

import com.ecui.ErnestsVilla.entity.Purchase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase,Integer> {
    List<Purchase> findByCustomerAccount(String customerAccount);
}
