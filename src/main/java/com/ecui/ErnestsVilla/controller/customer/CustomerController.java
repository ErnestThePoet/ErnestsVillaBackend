package com.ecui.ErnestsVilla.controller.customer;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.controller.customer.response.*;
import com.ecui.ErnestsVilla.service.customer.CustomerService;
import com.ecui.ErnestsVilla.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    ) {
        return customerService.getItemRecommendations(count);
    }

    @GetMapping(path = "/search")
    public ItemSearchResponse getItemRecommendations(
            @RequestParam String accessId,
            @RequestParam String keyword
    ) {
        var user = userService.getUserWithAccessId(accessId);
        if (user == null) {
            return new ItemSearchResponse("accessId无效");
        }

        return customerService.searchItems(keyword);
    }

    @GetMapping(path = "/get_item_detail")
    public GetItemDetailResponse getItemDetail(
            @RequestParam String accessId,
            @RequestParam Integer itemId
    ) {
        var user = userService.getUserWithAccessId(accessId);
        if (user == null) {
            return new GetItemDetailResponse("accessId无效");
        }

        return customerService.getItemDetail(itemId);
    }

    @GetMapping(path = "/get_cart_items")
    public GetCartItemsResponse getCartItems(@RequestParam String accessId) {
        var user = userService.getUserWithAccessId(accessId);

        if (user == null) {
            return new GetCartItemsResponse("accessId无效");
        }

        return customerService.getCartItems(user.getAccount());
    }

    @PostMapping(path = "/add_to_cart")
    public AddToCartResponse addToCart(
            @RequestParam String accessId,
            @RequestParam Integer itemId,
            @RequestParam Integer count
    ) {
        var user = userService.getUserWithAccessId(accessId);

        if (user == null) {
            return new AddToCartResponse("accessId无效");
        }

        return customerService.addToCart(user.getAccount(), itemId, count);
    }

    @DeleteMapping(path = "/delete_from_cart")
    public SuccessMsgResponse deleteFromCart(
            @RequestParam String accessId,
            @RequestParam Integer id
    ) {
        var user = userService.getUserWithAccessId(accessId);

        if (user == null) {
            return new SuccessMsgResponse("accessId无效");
        }

        return customerService.deleteFromCart(user.getAccount(), id);
    }

    @PutMapping(path = "/update_cart_item_count")
    public SuccessMsgResponse updateCartItemCount(
            @RequestParam String accessId,
            @RequestParam Integer id,
            @RequestParam Integer count
    ) {
        var user = userService.getUserWithAccessId(accessId);

        if (user == null) {
            return new SuccessMsgResponse("accessId无效");
        }

        return customerService.updateCartItemCount(user.getAccount(), id, count);
    }

    @DeleteMapping(path = "/clear_user_cart")
    public SuccessMsgResponse clearUserCart(@RequestParam String accessId) {
        var user = userService.getUserWithAccessId(accessId);

        if (user == null) {
            return new SuccessMsgResponse("accessId无效");
        }

        return customerService.clearUserCart(user.getAccount());
    }
}
