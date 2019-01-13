package com.nimble.sloth.dispatcher.repos.mongo;

import com.nimble.sloth.dispatcher.func.orders.Order;
import com.nimble.sloth.dispatcher.func.orders.OrdersRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class MongoOrdersRepository implements OrdersRepository {

    private final MongoTemplate template;

    public MongoOrdersRepository(final MongoTemplate template) {
        this.template = template;
    }

    @Override
    public boolean save(final Order order) {
        final Order result = template.save(order);
        return result != null;
    }

    @Override
    public Optional<Order> findById(final String orderId) {
        final Order order = template.findById(orderId, Order.class);
        return ofNullable(order);
    }

    @Override
    public Collection<Order> findByIds(final Collection<String> orderIds) {
        final Query query = findAllInIds(orderIds);
        return template.find(query, Order.class);
    }

    @Override
    public boolean markAsSentForPickup(final String orderId) {
        return updateSentFlag(orderId, "sentForPickUp");
    }

    @Override
    public boolean markAsSentForDelivery(final String orderId) {
        return updateSentFlag(orderId, "sentForDelivery");
    }

    private boolean updateSentFlag(
            final String orderId,
            final String sentFlag) {
        final Query query = findByIdQuery(orderId);
        final Update update = new Update().set(sentFlag, true);
        final Order result = template.findAndModify(query, update, Order.class);

        return ofNullable(result)
                .map(Order::isSentForPickUp)
                .orElse(false);
    }

    private Query findByIdQuery(final String orderId) {
        return new Query(where("orderId").in(orderId));
    }

    private Query findAllInIds(final Collection<String> orderIds) {
        return new Query(where("orderId").in(orderIds));
    }

    @Override
    public void removeByIds(final Collection<String> orderIds) {
        template.findAllAndRemove(findAllInIds(orderIds), Order.class);
    }
}
