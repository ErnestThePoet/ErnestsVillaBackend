package com.ecui.ErnestsVilla.controller.customer.response;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.controller.customer.response.objs.SinglePurchasedItemDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetPurchaseResponse extends SuccessMsgResponse {
    private List<SinglePurchasedItemDetail> purchasedItemDetails;

    public GetPurchaseResponse(){
        super();
    }

    public GetPurchaseResponse(String msg){
        super(msg);
    }
}
