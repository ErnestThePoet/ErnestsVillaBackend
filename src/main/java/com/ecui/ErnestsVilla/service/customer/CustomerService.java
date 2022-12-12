package com.ecui.ErnestsVilla.service.customer;

import com.ecui.ErnestsVilla.controller.common.objs.SingleItemDetail;
import com.ecui.ErnestsVilla.controller.customer.response.GetItemDetailResponse;
import com.ecui.ErnestsVilla.controller.customer.response.ItemRecommendationResponse;
import com.ecui.ErnestsVilla.controller.common.objs.SingleItemPreview;
import com.ecui.ErnestsVilla.controller.customer.response.ItemSearchResponse;
import com.ecui.ErnestsVilla.dao.ItemRepository;
import com.ecui.ErnestsVilla.dao.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public ItemRecommendationResponse getItemRecommendations(Integer count) {
        var items = itemRepository.findAll();
        count = Math.min(count, items.size());
        Collections.shuffle(items);

        var recommendedItems = items.subList(0, count);

        List<SingleItemPreview> recommendations = new ArrayList<>();

        for (var i : recommendedItems) {
            recommendations.add(new SingleItemPreview(i));
        }

        ItemRecommendationResponse response = new ItemRecommendationResponse();
        response.setRecommendations(recommendations);

        return response;
    }

    public ItemSearchResponse searchItems(String keyword){
        var items=itemRepository.findByNameLike("%%%s%%".formatted(keyword));

        List<SingleItemPreview> results=new ArrayList<>();

        for(var i:items){
            results.add(new SingleItemPreview(i));
        }

        ItemSearchResponse response=new ItemSearchResponse();

        response.setResults(results);

        return response;
    }

    public GetItemDetailResponse getItemDetail(Integer itemId){
        var itemOptional=itemRepository.findById(itemId);
        if(itemOptional.isEmpty()){
            return new GetItemDetailResponse("商品不存在");
        }

        SingleItemDetail detail=new SingleItemDetail(itemOptional.get());

        GetItemDetailResponse response=new GetItemDetailResponse();
        response.setItemDetail(detail);

        return response;
    }
}
