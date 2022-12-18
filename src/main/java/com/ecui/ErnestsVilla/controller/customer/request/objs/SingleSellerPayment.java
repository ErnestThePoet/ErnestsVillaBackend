package com.ecui.ErnestsVilla.controller.customer.request.objs;

import lombok.Data;

@Data
public class SingleSellerPayment {
    private String sellerAccount;
    private String totalPriceYuan;
    private Integer totalPriceCents;
}
