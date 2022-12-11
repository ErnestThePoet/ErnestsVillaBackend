package com.ecui.ErnestsVilla.dao;

import com.ecui.ErnestsVilla.entity.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item,Integer> {
    List<Item> findAll();
}
