package com.ecui.ErnestsVilla.service.customer;

import com.ecui.ErnestsVilla.controller.customer.response.ItemRecommendationResponse;
import com.ecui.ErnestsVilla.controller.customer.response.objs.ItemRecommendation;
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

        List<ItemRecommendation> recommendations = new ArrayList<>();

        for (var i : recommendedItems) {
            recommendations.add(new ItemRecommendation(i));
        }

        ItemRecommendationResponse response = new ItemRecommendationResponse();
        response.setRecommendations(recommendations);

        return response;
    }
}
