package com.gw.gmall.publisher.service;

import java.util.Map;

public interface PublisherService {
    int getDauTotal(String date );
    Map getDauHours(String date );
    Double getOrderAmount(String date);
    Map getOrderAmountHour(String date);
}
