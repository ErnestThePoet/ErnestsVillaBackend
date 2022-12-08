package com.ecui.ErnestsVilla.controller.seller.response;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadPreviewImageResponse extends SuccessMsgResponse {
    private String previewImageFileName;
}
