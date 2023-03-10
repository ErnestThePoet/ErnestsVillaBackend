package com.ecui.ErnestsVilla.controller.customer.response;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.controller.common.objs.SingleItemPreview;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ItemRecommendationResponse extends SuccessMsgResponse {
    private List<SingleItemPreview> recommendations;
    public ItemRecommendationResponse(){
        super();
    }

    public ItemRecommendationResponse(String msg){
        super(msg);
    }
}
