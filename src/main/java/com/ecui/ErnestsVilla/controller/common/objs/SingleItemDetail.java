package com.ecui.ErnestsVilla.controller.common.objs;

import com.ecui.ErnestsVilla.entity.Item;
import com.ecui.ErnestsVilla.utils.CurrencyHelper;
import lombok.Data;

@Data
public class SingleItemDetail {
    private Integer itemId;
    private String sellerAccount;
    private String name;
    private String description;
    private String previewImageFileName;
    private Integer remaining;
    private String priceYuan;
    private Integer purchaseCount;

    public SingleItemDetail(Item item) {
        this.itemId = item.getId();
        this.sellerAccount = item.getSellerAccount();
        this.name = item.getName();
        this.description = item.getDescription();
        this.previewImageFileName = item.getPreviewImageFileName();
        this.remaining = item.getRemaining();
        this.priceYuan = CurrencyHelper.getYuanFromCents(item.getPriceCents());
        this.purchaseCount = item.getPurchaseCount();
    }
}
