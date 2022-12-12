package com.ecui.ErnestsVilla.controller.common.objs;

import com.ecui.ErnestsVilla.entity.Item;
import lombok.Data;

@Data
public class SingleItemPreview {
    private Integer itemId;
    private String name;
    private String previewImageFileName;
    private Integer priceCents;
    private Integer purchaseCount;

    public SingleItemPreview(Item item) {
        this.itemId = item.getId();
        this.name = item.getName();
        this.previewImageFileName = item.getPreviewImageFileName();
        this.priceCents = item.getPriceCents();
        this.purchaseCount = item.getPurchaseCount();
    }
}