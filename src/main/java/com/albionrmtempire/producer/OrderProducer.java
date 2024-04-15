package com.albionrmtempire.producer;

import com.albionrmtempire.dataobject.Item;
import com.albionrmtempire.dataobject.Order;
import com.albionrmtempire.datatransferobject.OrderRequest;
import com.albionrmtempire.exception.NotFoundException;
import com.albionrmtempire.exception.UnsupportedBuyerException;
import com.albionrmtempire.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

import static com.albionrmtempire.util.ItemUtil.dropTierPrefix;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderProducer {
    private static final ZoneId UTC = ZoneOffset.UTC;
    private static final String BLACK_MARKET = "@BLACK_MARKET";

    private final ApplicationEventPublisher publisher;
    private final ItemRepository itemRepository;

    public String publishOrderEvent(OrderRequest orderRequest) {
        final Order order = parse(orderRequest);

        publisher.publishEvent(order);

        return order.item().systemName();
    }

    private Order parse(OrderRequest request) {
        if (!StringUtils.equals(request.buyer(), BLACK_MARKET)) {
            throw new UnsupportedBuyerException(request.buyer());
        }

        final String systemName = dropTierPrefix(request.itemGroupType());
        final Optional<Item> optionalItem = itemRepository.findById(systemName);

        return new Order(
                request.totalPrice(),
                request.amount(),
                request.tier(),
                request.enchantment(),
                request.quality(),
                request.isFinished(),
                request.buyer(),
                optionalItem.orElseThrow(() -> NotFoundException.ofItem(systemName)),
                ZonedDateTime.of(LocalDateTime.now(), UTC),
                ZonedDateTime.of(LocalDateTime.parse(request.expireDate()), UTC)
        );
    }
}
