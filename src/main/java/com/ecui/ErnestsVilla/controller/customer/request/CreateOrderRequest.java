package com.ecui.ErnestsVilla.controller.customer.request;

import com.ecui.ErnestsVilla.controller.common.request.AccessIdRequest;
import com.ecui.ErnestsVilla.controller.customer.request.objs.SingleItemOrderBrief;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateOrderRequest extends AccessIdRequest {
    private List<SingleItemOrderBrief> items;
    private String consigneeName;
    private String consigneeAddress;
    private String consigneePhoneNumber;
}
