package com.ecui.ErnestsVilla.dao;

import com.ecui.ErnestsVilla.entity.UnpaidPurchase;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UnpaidPurchaseRepository extends CrudRepository<UnpaidPurchase,Integer> {
    List<UnpaidPurchase> findByExpireTimeLessThan(Long time);
    List<UnpaidPurchase> findByCustomerAccount(String customerAccount);
    List<UnpaidPurchase> findByCustomerAccountAndPurchaseId(String customerAccount,Integer purchaseId);
    boolean existsByCustomerAccount(String customerAccount);

    @Modifying
    @Transactional
    void deleteByCustomerAccountAndPurchaseId(String customerAccount,Integer purchaseId);
}
