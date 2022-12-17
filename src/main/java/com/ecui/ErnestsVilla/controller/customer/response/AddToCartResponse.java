package com.ecui.ErnestsVilla.controller.customer.response;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddToCartResponse extends SuccessMsgResponse {
    private Integer id;

    public AddToCartResponse(){
        super();
    }

    public AddToCartResponse(String msg){
        super(msg);
    }
}
