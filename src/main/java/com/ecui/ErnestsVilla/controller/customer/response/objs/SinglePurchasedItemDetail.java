package com.ecui.ErnestsVilla.controller.customer.response.objs;

import com.ecui.ErnestsVilla.entity.Item;
import com.ecui.ErnestsVilla.entity.Purchase;
import com.ecui.ErnestsVilla.utils.CurrencyHelper;
import lombok.Data;

@Data
public class SinglePurchasedItemDetail {
    private Integer purchaseId;
    private String sellerAccount;
    private Integer itemId;
    private Integer count;
    private String paymentYuan;
    private Long purchaseTime;

    private String name;
    private String previewImageFileName;

    public SinglePurchasedItemDetail(Purchase purchase, Item item) {
        this.purchaseId = purchase.getPurchaseId();
        this.sellerAccount = purchase.getSellerAccount();
        this.itemId = purchase.getItemId();
        this.count = purchase.getCount();
        this.paymentYuan = CurrencyHelper.getYuanFromCents(purchase.getPaymentCents());
        this.purchaseTime = purchase.getPurchaseTime();
        this.name = item.getName();
        this.previewImageFileName = item.getPreviewImageFileName();
    }

    // For deleted items
    public SinglePurchasedItemDetail(Purchase purchase) {
        this.purchaseId = purchase.getPurchaseId();
        this.sellerAccount = purchase.getSellerAccount();
        this.itemId = purchase.getItemId();
        this.count = purchase.getCount();
        this.paymentYuan = CurrencyHelper.getYuanFromCents(purchase.getPaymentCents());
        this.purchaseTime = purchase.getPurchaseTime();
        this.name = "【已失效】商品ID=" + purchase.getItemId().toString();
        this.previewImageFileName = "404.webp";
    }
}
