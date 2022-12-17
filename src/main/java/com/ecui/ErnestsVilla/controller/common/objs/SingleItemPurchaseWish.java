package com.ecui.ErnestsVilla.controller.common.objs;

import com.ecui.ErnestsVilla.entity.CartItem;
import com.ecui.ErnestsVilla.entity.Item;
import lombok.Data;

@Data
public class SingleItemPurchaseWish {
    private Integer id;
    private SingleItemDetail item;
    private Integer count;

    public SingleItemPurchaseWish(CartItem cartItem, Item item) {
        this.id = cartItem.getId();
        this.item = new SingleItemDetail(item);
        this.count = cartItem.getCount();
    }
}
