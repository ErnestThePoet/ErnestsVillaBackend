package com.ecui.ErnestsVilla.dao;

import com.ecui.ErnestsVilla.entity.AccessId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccessIdRepository extends CrudRepository<AccessId,Integer> {
    Optional<AccessId> findByAccessIdHashed(String accessIdHashed);
    boolean existsByAccessIdHashed(String accessIdHashed);
    @Modifying
    @Transactional
    void deleteByAccessIdExpireLessThan(Long now);
}
