package com.pluralsight.orderfulfillment.order;

import org.apache.commons.lang3.StringUtils;

/**
 * Key and value for order statuses.
 *
 * @author Michael Hoffman, Pluralsight
 */
public enum OrderStatus {

    NEW("N", "New"), PROCESSING("P", "Processing");

    private String code;
    private String description;

    private OrderStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static OrderStatus getOrderStatusByCode(String code) {
        OrderStatus orderStatus = null;
        if (StringUtils.isNotBlank(code)) {
            for (OrderStatus status : OrderStatus.values()) {
                if (status.getCode().equals(code)) {
                    orderStatus = status;
                    break;
                }
            }
        }
        return orderStatus;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
