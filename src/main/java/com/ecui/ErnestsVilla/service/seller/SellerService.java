package com.ecui.ErnestsVilla.service.seller;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.dao.ItemRepository;
import com.ecui.ErnestsVilla.entity.Item;
import com.ecui.ErnestsVilla.entity.User;
import com.ecui.ErnestsVilla.service.objs.Validity;
import com.ecui.ErnestsVilla.utils.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SellerService {
    @Autowired
    private ItemRepository itemRepository;

    final String CDN_IMAGE_BASE_PATH = "../ErnestsVillaCDN/images/";

    private Validity checkItemValidity(Item item) {
        if (item.getName() == null
                || item.getName().isBlank()) {
            return new Validity("商品名称必须非空");
        }

        if (item.getDescription() == null
                || item.getDescription().isBlank()) {
            return new Validity("商品描述必须非空");
        }

        if (item.getPreviewImageFileName() == null
                || item.getPreviewImageFileName().isBlank()) {
            return new Validity("商品预览图文件名必须非空");
        }

        if (item.getPriceCents() == null
                || item.getPriceCents() <= 0
                || item.getPriceCents() > 10000000) {
            return new Validity("商品价格必须在0.01元至100,000.00元之间");
        }

        if (item.getPurchaseCount() == null
                || item.getPurchaseCount() < 0) {
            return new Validity("商品销售量必须为非负数");
        }

        if (item.getRemaining() == null
                || item.getRemaining() <= 0) {
            return new Validity("商品库存量必须为正");
        }

        // seller account is set or checked independently
        if (item.getSellerAccount() == null) {
            return new Validity("商家账号不能为空");
        }

        return new Validity();
    }

    public String savePreviewImage(MultipartFile imageFile) {
        var splited = imageFile.getOriginalFilename().split("\\.");
        String dottedFileExtension = "." + splited[splited.length - 1];

        StringBuilder saveFileName = new StringBuilder(String.valueOf(DateTimeHelper.getNow()));

        while (Files.exists(
                Path.of(CDN_IMAGE_BASE_PATH + saveFileName + dottedFileExtension))) {
            saveFileName.append("-1");
        }

        try {
            Files.write(
                    Path.of(CDN_IMAGE_BASE_PATH + saveFileName + dottedFileExtension),
                    imageFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return saveFileName + dottedFileExtension;
    }

    public SuccessMsgResponse publishItem(Item item) {
        var validity=checkItemValidity(item);
        if(!validity.isValid()){
            return new SuccessMsgResponse(validity.message());
        }

        itemRepository.save(item);

        return new SuccessMsgResponse();
    }

    public SuccessMsgResponse updateItem(User operator, Item newItem) {
        var itemOptional=itemRepository.findById(newItem.getId());

        if(itemOptional.isEmpty()){
            return new SuccessMsgResponse("商品不存在");
        }

        var item=itemOptional.get();

        if(!operator.getAccount().equals(item.getSellerAccount())){
            return new SuccessMsgResponse("操作者非商品商家");
        }

        item.setDescription(newItem.getDescription());
        item.setName(newItem.getName());
        item.setPreviewImageFileName(newItem.getPreviewImageFileName());
        item.setPriceCents(newItem.getPriceCents());
        item.setRemaining(newItem.getRemaining());

        var validity=checkItemValidity(item);
        if(!validity.isValid()){
            return new SuccessMsgResponse(validity.message());
        }

        itemRepository.save(item);

        return new SuccessMsgResponse();
    }

    public SuccessMsgResponse deleteItem(User operator,Integer itemId){
        var itemOptional=itemRepository.findById(itemId);

        if(itemOptional.isEmpty()){
            return new SuccessMsgResponse("商品不存在");
        }

        var item=itemOptional.get();

        if(!operator.getAccount().equals(item.getSellerAccount())){
            return new SuccessMsgResponse("操作者非商品商家");
        }

        itemRepository.deleteById(itemId);

        return new SuccessMsgResponse();
    }
}
