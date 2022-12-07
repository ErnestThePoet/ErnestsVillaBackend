package com.ecui.ErnestsVilla.dao;

import com.ecui.ErnestsVilla.entity.Purchase;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase,Integer> {
}
