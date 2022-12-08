package com.ecui.ErnestsVilla.service.seller;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.dao.ItemRepository;
import com.ecui.ErnestsVilla.entity.Item;
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
        itemRepository.save(item);

        return new SuccessMsgResponse();
    }
}
