package com.ecui.ErnestsVilla.service.seller;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.dao.ItemRepository;
import com.ecui.ErnestsVilla.entity.Item;
import com.ecui.ErnestsVilla.utils.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SellerService {
    @Autowired
    private ItemRepository itemRepository;

    final String CDN_IMAGE_BASE_PATH = "../ErnestsVillaCDN/images/";

    public String savePreviewImage(MultipartFile imageFile) {
        System.out.println(imageFile.getName());
        System.out.println(imageFile.getOriginalFilename());
//        String saveFileName= String.valueOf(DateTimeHelper.getNow());
//
//        while(Files.exists(Path.of(CDN_IMAGE_BASE_PATH+"/"+saveFileName+imageFile.))){
//            saveFileName+="-1";
//        }

        return "";
    }

    public SuccessMsgResponse publishItem(Item item) {
        //itemRepository.save(item);

        return new SuccessMsgResponse();
    }
}
