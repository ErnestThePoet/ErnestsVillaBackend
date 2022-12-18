package com.ecui.ErnestsVilla.controller.customer.response;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateOrderResponse extends SuccessMsgResponse {
    private Integer purchaseId;
    private String totalPriceYuan;
    private Integer totalPriceCents;
    private Long expireTime;

    public CreateOrderResponse(){
        super();
    }

    public CreateOrderResponse(String msg){
        super(msg);
    }
}
