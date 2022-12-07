package com.ecui.ErnestsVilla.controller.seller;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.entity.Item;
import com.ecui.ErnestsVilla.service.seller.SellerService;
import com.ecui.ErnestsVilla.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/seller")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private UserService userService;
    @PostMapping(path = "/publish")
    public SuccessMsgResponse publishItem(
            @RequestParam String accessId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam MultipartFile previewImage,
            @RequestParam Integer remaining,
            @RequestParam Integer priceCents){
        var user=userService.getUser(accessId);
        if(user==null){
            return new SuccessMsgResponse("accessId无效");
        }

        String previewImageFileName=sellerService.savePreviewImage(previewImage);

        Item item=new Item();
        item.setDescription(description);
        item.setName(name);
        item.setPreviewImageFileName(previewImageFileName);
        item.setPriceCents(priceCents);
        item.setRemaining(remaining);
        item.setSellerAccount(user.getAccount());

        return sellerService.publishItem(item);
    }
}
