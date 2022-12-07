package com.ecui.ErnestsVilla.dao;

import com.ecui.ErnestsVilla.entity.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item,Integer> {
}
