package com.ecui.ErnestsVilla.controller.customer.response;

import com.ecui.ErnestsVilla.controller.common.objs.SingleItemPurchaseWish;
import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetCartItemsResponse extends SuccessMsgResponse {
    private List<SingleItemPurchaseWish> cartItems;

    public GetCartItemsResponse() {
        super();
    }

    public GetCartItemsResponse(String msg) {
        super(msg);
    }
}
