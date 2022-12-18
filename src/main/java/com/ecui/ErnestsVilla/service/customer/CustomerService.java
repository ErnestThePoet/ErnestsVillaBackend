package com.ecui.ErnestsVilla.service.customer;

import com.ecui.ErnestsVilla.controller.common.objs.SingleItemDetail;
import com.ecui.ErnestsVilla.controller.common.objs.SingleItemPurchaseWish;
import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.controller.customer.request.objs.SingleItemOrderBrief;
import com.ecui.ErnestsVilla.controller.customer.response.*;
import com.ecui.ErnestsVilla.controller.common.objs.SingleItemPreview;
import com.ecui.ErnestsVilla.dao.CartItemRepository;
import com.ecui.ErnestsVilla.dao.ItemRepository;
import com.ecui.ErnestsVilla.dao.PurchaseRepository;
import com.ecui.ErnestsVilla.dao.UnpaidPurchaseRepository;
import com.ecui.ErnestsVilla.entity.CartItem;
import com.ecui.ErnestsVilla.entity.UnpaidPurchase;
import com.ecui.ErnestsVilla.utils.CurrencyHelper;
import com.ecui.ErnestsVilla.utils.DateTimeHelper;
import com.ecui.ErnestsVilla.utils.ItemHelper;
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
    private UnpaidPurchaseRepository unpaidPurchaseRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

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

    public ItemSearchResponse searchItems(String keyword) {
        var items = itemRepository.findByNameLike("%%%s%%".formatted(keyword));

        List<SingleItemPreview> results = new ArrayList<>();

        for (var i : items) {
            results.add(new SingleItemPreview(i));
        }

        ItemSearchResponse response = new ItemSearchResponse();

        response.setResults(results);

        return response;
    }

    public GetItemDetailResponse getItemDetail(Integer itemId) {
        var itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) {
            return new GetItemDetailResponse("商品不存在");
        }

        SingleItemDetail detail = new SingleItemDetail(itemOptional.get());

        GetItemDetailResponse response = new GetItemDetailResponse();
        response.setItemDetail(detail);

        return response;
    }

    public GetCartItemsResponse getCartItems(String account) {
        var cartItemEntries = cartItemRepository.findByCustomerAccount(account);

        List<SingleItemPurchaseWish> wishes = new ArrayList<>();

        for (var i : cartItemEntries) {
            var itemDetailOptional = itemRepository.findById(i.getItemId());
            if (itemDetailOptional.isEmpty()) {
                continue;
            }

            wishes.add(new SingleItemPurchaseWish(i, itemDetailOptional.get()));
        }

        GetCartItemsResponse response = new GetCartItemsResponse();

        response.setCartItems(wishes);

        return response;
    }

    public AddToCartResponse addToCart(String account, Integer itemId, Integer count) {
        if (count < 1) {
            return new AddToCartResponse("购买数量最少为1");
        }

        if (!itemRepository.existsById(itemId)) {
            return new AddToCartResponse("商品不存在");
        }

        if (cartItemRepository.existsByItemIdAndCustomerAccount(itemId, account)) {
            return new AddToCartResponse("用户购物车中已存在该商品");
        }

        CartItem cartItem = new CartItem();

        cartItem.setItemId(itemId);
        cartItem.setCustomerAccount(account);
        cartItem.setCount(count);

        cartItemRepository.save(cartItem);

        AddToCartResponse response = new AddToCartResponse();
        response.setId(cartItem.getId());

        return response;
    }

    public SuccessMsgResponse deleteFromCart(String account, Integer id) {
        if (!cartItemRepository.existsByIdAndCustomerAccount(id, account)) {
            return new SuccessMsgResponse("用户购物车记录不存在");
        }

        cartItemRepository.deleteById(id);
        return new SuccessMsgResponse();
    }

    public SuccessMsgResponse updateCartItemCount(
            String account, Integer id, Integer count) {
        var cartItemOptional = cartItemRepository.findById(id);

        if (cartItemOptional.isEmpty()
                || !cartItemOptional.get().getCustomerAccount().equals(account)) {
            return new SuccessMsgResponse("用户购物车记录不存在");
        }

        var cartItem = cartItemOptional.get();

        cartItem.setCount(count);

        cartItemRepository.save(cartItem);

        return new SuccessMsgResponse();
    }

    public SuccessMsgResponse clearUserCart(String account) {
        cartItemRepository.deleteByCustomerAccount(account);
        return new SuccessMsgResponse();
    }

    private String getSorryMessage(String itemName, String sorryContent) {
        return "很抱歉，您选择的商品【%s】%s".formatted(
                ItemHelper.getShortItemName(itemName), sorryContent);
    }

    private String getSorryMessage(int itemId, String sorryContent) {
        return "很抱歉，您选择的ID为【%d】的商品%s".formatted(itemId, sorryContent);
    }

    private long getUnpaidPurchaseExpire(long now) {
        return now + 15 * DateTimeHelper.MS_PER_MIN;
    }

    public CreateOrderResponse createOrder(
            String customerAccount,
            List<SingleItemOrderBrief> items,
            String consigneeName,
            String consigneeAddress,
            String consigneePhoneNumber) {
        if (unpaidPurchaseRepository.existsByCustomerAccount(customerAccount)) {
            return new CreateOrderResponse("您尚有未支付的订单");
        }

        List<UnpaidPurchase> unpaidPurchases = new ArrayList<>();

        Integer purchaseId = 0;
        int totalPriceCents = 0;

        long now = DateTimeHelper.getNow();
        long expireTime = getUnpaidPurchaseExpire(now);

        boolean isFirstSave = true;
        for (var i : items) {
            var itemOptional = itemRepository.findById(i.getItemId());

            if (itemOptional.isEmpty()) {
                return new CreateOrderResponse(
                        getSorryMessage(i.getItemId(), "不存在"));
            }

            var item = itemOptional.get();

            if (item.getRemaining() < i.getCount()) {
                return new CreateOrderResponse(
                        getSorryMessage(item.getName(), "库存数量不足"));
            }

            item.setRemaining(item.getRemaining() - i.getCount());

            itemRepository.save(item);

            int currentItemTotalPriceCents = item.getPriceCents() * i.getCount();

            UnpaidPurchase unpaidPurchase = new UnpaidPurchase();
            unpaidPurchase.setConsigneeAddress(consigneeAddress);
            unpaidPurchase.setConsigneeName(consigneeName);
            unpaidPurchase.setConsigneePhoneNumber(consigneePhoneNumber);
            unpaidPurchase.setCount(i.getCount());
            unpaidPurchase.setCreateTime(now);
            unpaidPurchase.setCustomerAccount(customerAccount);
            unpaidPurchase.setExpireTime(expireTime);
            unpaidPurchase.setItemId(i.getItemId());
            unpaidPurchase.setPaymentCents(currentItemTotalPriceCents);
            if (!isFirstSave) {
                unpaidPurchase.setPurchaseId(purchaseId);
            }
            unpaidPurchase.setSellerAccount(item.getSellerAccount());

            unpaidPurchaseRepository.save(unpaidPurchase);

            if (isFirstSave) {
                purchaseId = unpaidPurchase.getId();
                unpaidPurchase.setPurchaseId(purchaseId);
                unpaidPurchaseRepository.save(unpaidPurchase);
                isFirstSave = false;
            }

            totalPriceCents += currentItemTotalPriceCents;
        }

        var response = new CreateOrderResponse();
        response.setExpireTime(expireTime);
        response.setPurchaseId(purchaseId);
        response.setTotalPriceCents(totalPriceCents);
        response.setTotalPriceYuan(CurrencyHelper.getYuanFromCents(totalPriceCents));

        return response;
    }
}
