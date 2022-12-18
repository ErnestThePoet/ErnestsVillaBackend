package com.ecui.ErnestsVilla.controller.customer.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetUnpaidPurchaseResponse extends CreateOrderResponse {
    private Boolean hasUnpaidPurchase;

    public GetUnpaidPurchaseResponse() {
        super();
    }

    public GetUnpaidPurchaseResponse(String msg) {
        super(msg);
    }
}
