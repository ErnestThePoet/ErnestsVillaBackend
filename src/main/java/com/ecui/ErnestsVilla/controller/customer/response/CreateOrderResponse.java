package com.ecui.ErnestsVilla.controller.customer.response;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.controller.customer.request.objs.SingleSellerPayment;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateOrderResponse extends SuccessMsgResponse {
    private Integer purchaseId;
    private String totalPriceYuan;
    private Integer totalPriceCents;
    private Long expireTime;

    private List<SingleSellerPayment> sellerPayments;

    public CreateOrderResponse(){
        super();
    }

    public CreateOrderResponse(String msg){
        super(msg);
    }
}
