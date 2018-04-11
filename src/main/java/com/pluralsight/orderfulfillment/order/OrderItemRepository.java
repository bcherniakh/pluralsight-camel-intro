package com.pluralsight.orderfulfillment.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Repository for OrderItemEntity data.
 *
 * @author Michael Hoffman, Pluralsight
 */
public interface OrderItemRepository extends
        JpaRepository<OrderItemEntity, Integer> {

    @Query("select oi from OrderItemEntity oi where oi.order.id = ?1")
    List<OrderItemEntity> findByOrderId(long orderId);

    @Modifying
    @Query("update OrderItemEntity oi set oi.status = ?1, oi.lastUpdate = ?2 where oi.order.id in (?3)")
    int updateStatus(String code, Date lastUpdate, List<Long> orderIds);

}
