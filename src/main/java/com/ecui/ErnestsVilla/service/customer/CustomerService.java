package com.ecui.ErnestsVilla.service.customer;

import com.ecui.ErnestsVilla.controller.common.objs.SingleItemDetail;
import com.ecui.ErnestsVilla.controller.common.objs.SingleItemPurchaseWish;
import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.controller.customer.request.objs.SingleItemOrderBrief;
import com.ecui.ErnestsVilla.controller.customer.request.objs.SingleSellerPayment;
import com.ecui.ErnestsVilla.controller.customer.response.*;
import com.ecui.ErnestsVilla.controller.common.objs.SingleItemPreview;
import com.ecui.ErnestsVilla.controller.customer.response.objs.SinglePurchasedItemDetail;
import com.ecui.ErnestsVilla.dao.*;
import com.ecui.ErnestsVilla.entity.CartItem;
import com.ecui.ErnestsVilla.entity.Purchase;
import com.ecui.ErnestsVilla.entity.UnpaidPurchase;
import com.ecui.ErnestsVilla.entity.User;
import com.ecui.ErnestsVilla.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

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
            return new GetItemDetailResponse("???????????????");
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
            return new AddToCartResponse("?????????????????????1");
        }

        if (!itemRepository.existsById(itemId)) {
            return new AddToCartResponse("???????????????");
        }

        if (cartItemRepository.existsByItemIdAndCustomerAccount(itemId, account)) {
            return new AddToCartResponse("????????????????????????????????????");
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
            return new SuccessMsgResponse("??????????????????????????????");
        }

        cartItemRepository.deleteById(id);
        return new SuccessMsgResponse();
    }

    public SuccessMsgResponse updateCartItemCount(
            String account, Integer id, Integer count) {
        var cartItemOptional = cartItemRepository.findById(id);

        if (cartItemOptional.isEmpty()
                || !cartItemOptional.get().getCustomerAccount().equals(account)) {
            return new SuccessMsgResponse("??????????????????????????????");
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

    public GetUnpaidPurchaseResponse getUnpaidPurchase(String customerAccount) {
        var unpaidPurchases = unpaidPurchaseRepository.findByCustomerAccount(customerAccount);
        if (unpaidPurchases.size() == 0) {
            var response = new GetUnpaidPurchaseResponse();
            response.setHasUnpaidPurchase(false);
            return response;
        }

        int totalPriceCents = 0;

        Map<String, Integer> sellerTotalPriceCents = new HashMap<>();
        Map<String, User> sellerUser = new HashMap<>();

        for (var i : unpaidPurchases) {
            if (!sellerUser.containsKey(i.getSellerAccount())) {
                var userOptional = userRepository.findByAccount(i.getSellerAccount());
                if (userOptional.isEmpty()) {
                    return new GetUnpaidPurchaseResponse(
                            "?????????%s????????????".formatted(i.getSellerAccount()));
                }
                sellerUser.put(i.getSellerAccount(), userOptional.get());
            }

            totalPriceCents += i.getPaymentCents();
            if (sellerTotalPriceCents.containsKey(i.getSellerAccount())) {
                sellerTotalPriceCents.put(
                        i.getSellerAccount(),
                        sellerTotalPriceCents.get(i.getSellerAccount()) + i.getPaymentCents());
            } else {
                sellerTotalPriceCents.put(i.getSellerAccount(), i.getPaymentCents());
            }
        }

        List<SingleSellerPayment> sellerPayments = new ArrayList<>();

        for (var i : sellerTotalPriceCents.entrySet()) {
            var sellerPayment = new SingleSellerPayment();
            sellerPayment.setSellerAccount(i.getKey());
            sellerPayment.setSellerBank1Account(sellerUser.get(i.getKey()).getBank1Account());
            sellerPayment.setSellerBank2Account(sellerUser.get(i.getKey()).getBank2Account());
            sellerPayment.setTotalPriceCents(i.getValue());
            sellerPayment.setTotalPriceYuan(CurrencyHelper.getYuanFromCents(i.getValue()));
            sellerPayments.add(sellerPayment);
        }

        var response = new GetUnpaidPurchaseResponse();
        response.setHasUnpaidPurchase(true);
        response.setExpireTime(unpaidPurchases.get(0).getExpireTime());
        response.setPurchaseId(unpaidPurchases.get(0).getPurchaseId());
        response.setTotalPriceCents(totalPriceCents);
        response.setTotalPriceYuan(CurrencyHelper.getYuanFromCents(totalPriceCents));
        response.setSellerPayments(sellerPayments);

        return response;
    }

    private String getSorryMessage(String itemName, String sorryContent) {
        return "?????????????????????????????????%s???%s".formatted(
                ItemHelper.getShortItemName(itemName), sorryContent);
    }

    private String getSorryMessage(int itemId, String sorryContent) {
        return "????????????????????????ID??????%d????????????%s".formatted(itemId, sorryContent);
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
            return new CreateOrderResponse("???????????????????????????");
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
                        getSorryMessage(i.getItemId(), "?????????"));
            }

            var item = itemOptional.get();

            if (item.getRemaining() < i.getCount()) {
                return new CreateOrderResponse(
                        getSorryMessage(item.getName(), "??????????????????"));
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
            if (isFirstSave) {
                unpaidPurchase.setPurchaseId(-1);
            } else {
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

            unpaidPurchases.add(unpaidPurchase);

            totalPriceCents += currentItemTotalPriceCents;
        }

        Map<String, Integer> sellerTotalPriceCents = new HashMap<>();
        Map<String, User> sellerUser = new HashMap<>();

        for (var i : unpaidPurchases) {
            if (!sellerUser.containsKey(i.getSellerAccount())) {
                var userOptional = userRepository.findByAccount(i.getSellerAccount());
                if (userOptional.isEmpty()) {
                    return new GetUnpaidPurchaseResponse(
                            "?????????%s????????????".formatted(i.getSellerAccount()));
                }
                sellerUser.put(i.getSellerAccount(), userOptional.get());
            }

            if (sellerTotalPriceCents.containsKey(i.getSellerAccount())) {
                sellerTotalPriceCents.put(
                        i.getSellerAccount(),
                        sellerTotalPriceCents.get(i.getSellerAccount()) + i.getPaymentCents());
            } else {
                sellerTotalPriceCents.put(i.getSellerAccount(), i.getPaymentCents());
            }
        }

        List<SingleSellerPayment> sellerPayments = new ArrayList<>();

        for (var i : sellerTotalPriceCents.entrySet()) {
            var sellerPayment = new SingleSellerPayment();
            sellerPayment.setSellerAccount(i.getKey());
            sellerPayment.setSellerBank1Account(sellerUser.get(i.getKey()).getBank1Account());
            sellerPayment.setSellerBank2Account(sellerUser.get(i.getKey()).getBank2Account());
            sellerPayment.setTotalPriceCents(i.getValue());
            sellerPayment.setTotalPriceYuan(CurrencyHelper.getYuanFromCents(i.getValue()));
            sellerPayments.add(sellerPayment);
        }

        var response = new CreateOrderResponse();
        response.setExpireTime(expireTime);
        response.setPurchaseId(purchaseId);
        response.setTotalPriceCents(totalPriceCents);
        response.setTotalPriceYuan(CurrencyHelper.getYuanFromCents(totalPriceCents));
        response.setSellerPayments(sellerPayments);

        return response;
    }

    public SuccessMsgResponse cancelOrder(
            String customerAccount,
            Integer purchaseId) {
        unpaidPurchaseRepository.deleteByCustomerAccountAndPurchaseId(
                customerAccount, purchaseId);
        return new SuccessMsgResponse();
    }

    public SuccessMsgResponse confirmOrder(
            String customerAccount,
            Integer purchaseId,

            String timeStamp,
            String pimdBase64,
            String dsBase64
    ) {
        var unpaidPurchases = unpaidPurchaseRepository
                .findByCustomerAccountAndPurchaseId(customerAccount, purchaseId);
        if (unpaidPurchases.size() == 0) {
            return new SuccessMsgResponse("???????????????");
        }

        byte[] oimd = HashHelper.sha256(timeStamp + purchaseId.toString());
        byte[] pimd = Base64.getDecoder().decode(pimdBase64);

        byte[] pomd = HashHelper.sha256(ByteArrayUtil.concat(pimd, oimd));

        if (!ByteArrayUtil.equals(pomd, Base64.getDecoder().decode(dsBase64))) {
            return new SuccessMsgResponse("????????????????????????");
        }

        unpaidPurchaseRepository.deleteByCustomerAccountAndPurchaseId(customerAccount, purchaseId);

        long purchaseTime = DateTimeHelper.getNow();

        List<Purchase> purchases = new ArrayList<>();

        for (var i : unpaidPurchases) {
            purchases.add(new Purchase(i, purchaseTime));

            var itemOptional = itemRepository.findById(i.getItemId());

            // When this happens, the seller would be informed about this paid order
            if (itemOptional.isEmpty()) {
                continue;
            }

            var item = itemOptional.get();

            item.setPurchaseCount(item.getPurchaseCount() + i.getCount());

            itemRepository.save(item);
        }

        purchaseRepository.saveAll(purchases);

        return new SuccessMsgResponse();
    }

    public GetPurchaseResponse getPurchase(String customerAccount) {
        List<SinglePurchasedItemDetail> purchasedItemDetails = new ArrayList<>();

        var purchases = purchaseRepository.findByCustomerAccount(customerAccount);

        for (var i : purchases) {
            var itemOptional = itemRepository.findById(i.getItemId());
            if (itemOptional.isEmpty()) {
                purchasedItemDetails.add(new SinglePurchasedItemDetail(i));
                continue;
            }

            var item = itemOptional.get();

            purchasedItemDetails.add(new SinglePurchasedItemDetail(i, item));
        }

        var response = new GetPurchaseResponse();
        response.setPurchasedItemDetails(purchasedItemDetails);

        return response;
    }
}
