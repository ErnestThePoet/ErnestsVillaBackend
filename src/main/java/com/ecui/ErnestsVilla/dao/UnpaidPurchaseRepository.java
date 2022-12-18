package com.ecui.ErnestsVilla.dao;

import com.ecui.ErnestsVilla.entity.UnpaidPurchase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UnpaidPurchaseRepository extends CrudRepository<UnpaidPurchase,Integer> {
    List<UnpaidPurchase> findByExpireTimeLessThan(Long time);
    boolean existsByCustomerAccount(String customerAccount);
}
