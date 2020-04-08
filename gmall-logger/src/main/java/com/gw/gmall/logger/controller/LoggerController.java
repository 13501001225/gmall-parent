package com.gw.gmall.logger.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import com.gw.gmall.common.constants.GmallConstants;

@RestController
@Slf4j
class LoggerController {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;


    @PostMapping("log")
    public String log(@RequestParam("logString") String logString){
        //补充时间戳
        JSONObject jsonObject = JSON.parseObject(logString);
        jsonObject.put("ts",System.currentTimeMillis());

        //日志落盘
        String jsonString = jsonObject.toJSONString();
        log.info(jsonObject.toJSONString());

        //推送到kafka
        if( "startup".equals( jsonObject.getString("type"))){
            kafkaTemplate.send(GmallConstants.KAFKA_TOPIC_STARTUP,jsonString);
        }else{
            kafkaTemplate.send(GmallConstants.KAFKA_TOPIC_EVENT,jsonString);
        }

//        log.info(logString);
        return "success";

    }
}
