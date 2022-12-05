package com.ecui.ErnestsVilla.dao;

import com.ecui.ErnestsVilla.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {
    boolean existsByAccount(String account);

    Optional<User> findByAccount(String account);
    Optional<User> findBySessionIdHashed(String sessionIdHashed);
}
