package com.ecui.ErnestsVilla.controller.customer;

import com.ecui.ErnestsVilla.controller.customer.response.GetItemDetailResponse;
import com.ecui.ErnestsVilla.controller.customer.response.ItemRecommendationResponse;
import com.ecui.ErnestsVilla.controller.customer.response.ItemSearchResponse;
import com.ecui.ErnestsVilla.service.customer.CustomerService;
import com.ecui.ErnestsVilla.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/get_recommendations")
    public ItemRecommendationResponse getItemRecommendations(
            @RequestParam Integer count
    ){
        return customerService.getItemRecommendations(count);
    }

    @GetMapping(path = "/search")
    public ItemSearchResponse getItemRecommendations(
            @RequestParam String accessId,
            @RequestParam String keyword
    ){
        var user=userService.getUserWithAccessId(accessId);
        if(user==null){
            return new ItemSearchResponse("accessId无效");
        }

        return customerService.searchItems(keyword);
    }

    @GetMapping(path = "/get_item_detail")
    public GetItemDetailResponse getItemDetail(
            @RequestParam String accessId,
            @RequestParam Integer itemId
    ){
        var user=userService.getUserWithAccessId(accessId);
        if(user==null){
            return new GetItemDetailResponse("accessId无效");
        }

        return customerService.getItemDetail(itemId);
    }
}
