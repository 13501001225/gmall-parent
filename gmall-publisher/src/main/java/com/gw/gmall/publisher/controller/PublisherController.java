package com.gw.gmall.publisher.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gw.gmall.publisher.service.PublisherService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class PublisherController {

  @Autowired
  PublisherService publisherService;

    @GetMapping("realtime-total")
    public String realtimeHourDate(@RequestParam("date") String date) {
         List<Map> list = new ArrayList<>();
         // 日活总数
        int dauTotal = publisherService.getDauTotal(date);
        Map dauMap=new HashMap<String,Object>();
        dauMap.put("id","dau");
        dauMap.put("name","新增日活");
        dauMap.put("value",dauTotal);
        list.add(dauMap);

        Map orderAmountMap=new HashMap();
        orderAmountMap.put("id","order_amount");
        orderAmountMap.put("name","新增交易额");
        Double orderAmount = publisherService.getOrderAmount(date);
        orderAmountMap.put("value",orderAmount);
        list.add(orderAmountMap);


        return JSON.toJSONString(list);
    }


   @GetMapping("realtime-hours")
  public String realtimeHourDate(@RequestParam("id") String id,@RequestParam("date") String date){

       String  yesterdayDateString="";
       try {
           Date dateToday = new SimpleDateFormat("yyyy-MM-dd").parse(date);
           Date dateYesterday = DateUtils.addDays(dateToday, -1);
           yesterdayDateString=new SimpleDateFormat("yyyy-MM-dd").format(dateYesterday);

       } catch (ParseException e) {
           e.printStackTrace();
       }

      if( "dau".equals(id)){
          Map dauHoursToday = publisherService.getDauHours(date);
          JSONObject jsonObject = new JSONObject();
          jsonObject.put("today",dauHoursToday);
          Map dauHoursYesterday = publisherService.getDauHours(yesterdayDateString);
          jsonObject.put("yesterday",dauHoursYesterday);
          return jsonObject.toJSONString();
      } else if("order_amount".equals(id)){
          Map hourMap=new HashMap();
          Map orderHourTMap = publisherService.getOrderAmountHour(date);
          Map orderHourYMap = publisherService.getOrderAmountHour(yesterdayDateString);
          hourMap.put("yesterday",orderHourYMap);
          hourMap.put("today",orderHourTMap);
          return JSON.toJSONString(hourMap);

      }

       return null;
  }

}
