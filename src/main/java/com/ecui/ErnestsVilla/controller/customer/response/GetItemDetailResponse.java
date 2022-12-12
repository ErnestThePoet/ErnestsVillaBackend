package com.ecui.ErnestsVilla.controller.customer.response;

import com.ecui.ErnestsVilla.controller.common.objs.SingleItemDetail;
import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetItemDetailResponse extends SuccessMsgResponse {
    private SingleItemDetail itemDetail;

    public GetItemDetailResponse(){
        super();
    }

    public GetItemDetailResponse(String msg){
        super(msg);
    }
}
