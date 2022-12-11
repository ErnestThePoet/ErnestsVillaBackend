package com.ecui.ErnestsVilla.controller.customer.response.objs;

import com.ecui.ErnestsVilla.entity.Item;
import lombok.Data;

@Data
public class ItemRecommendation {
    private Integer itemId;
    private String name;
    private String previewImageFileName;
    private Integer priceCents;
    private Integer purchaseCount;

    public ItemRecommendation(Item item) {
        this.itemId = item.getId();
        this.name = item.getName();
        this.previewImageFileName = item.getPreviewImageFileName();
        this.priceCents = item.getPriceCents();
        this.purchaseCount = item.getPurchaseCount();
    }
}
