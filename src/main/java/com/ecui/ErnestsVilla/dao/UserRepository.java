package com.ecui.ErnestsVilla.dao;

import com.ecui.ErnestsVilla.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {
    boolean existsByAccount(String account);
    boolean existsByBank1Account(String bank1Account);
    boolean existsByBank2Account(String bank2Account);

    Optional<User> findByAccount(String account);
    Optional<User> findBySessionIdHashed(String sessionIdHashed);
}
