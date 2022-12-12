package com.ecui.ErnestsVilla.controller.customer.response;

import com.ecui.ErnestsVilla.controller.common.objs.SingleItemPreview;
import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ItemSearchResponse extends SuccessMsgResponse {
    private List<SingleItemPreview> results;

    public ItemSearchResponse() {
        super();
    }

    public ItemSearchResponse(String msg) {
        super(msg);
    }
}
