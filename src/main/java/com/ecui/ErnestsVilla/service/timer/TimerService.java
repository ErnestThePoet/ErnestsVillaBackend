package com.ecui.ErnestsVilla.service.timer;

import com.ecui.ErnestsVilla.dao.*;
import com.ecui.ErnestsVilla.utils.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TimerService {
    @Autowired
    UnpaidPurchaseRepository unpaidPurchaseRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    AccessIdRepository accessIdRepository;

    @Scheduled(fixedRate = 20 * DateTimeHelper.MS_PER_SEC)
    public void clearExpiredUnpaidPurchases() {
        var expiredUnpaidPurchases =
                unpaidPurchaseRepository.findByExpireTimeLessThan(DateTimeHelper.getNow());

        for (var i : expiredUnpaidPurchases) {
            unpaidPurchaseRepository.deleteById(i.getId());
            var itemOptional = itemRepository.findById(i.getItemId());
            if (itemOptional.isEmpty()) {
                continue;
            }

            var item = itemOptional.get();

            item.setRemaining(item.getRemaining() + i.getCount());

            itemRepository.save(item);
        }
    }

    @Scheduled(fixedRate = DateTimeHelper.MS_PER_MIN)
    public void clearExpiredAccessIds() {
        accessIdRepository.deleteByAccessIdExpireLessThan(DateTimeHelper.getNow());
    }
}
