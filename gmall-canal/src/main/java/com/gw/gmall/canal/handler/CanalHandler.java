package com.gw.gmall.canal.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.gw.gmall.canal.utils.MyKafkaSender;
import com.gw.gmall.common.constants.GmallConstants;

import java.util.List;


public class CanalHandler {

       CanalEntry.EventType eventType;

       String tableName;

       List<CanalEntry.RowData> rowDataList;

    public CanalHandler(CanalEntry.EventType eventType, String tableName, List<CanalEntry.RowData> rowDataList) {
        this.eventType = eventType;
        this.tableName = tableName;
        this.rowDataList = rowDataList;
    }

    public void handle(){
        //下单操作
        if("order_info".equals(tableName)&& CanalEntry.EventType.INSERT==eventType){
            rowDateList2Kafka( GmallConstants.KAFKA_TOPIC_ORDER);
        }else if ("user_info".equals(tableName)&& (CanalEntry.EventType.INSERT==eventType||CanalEntry.EventType.UPDATE==eventType)) {
            rowDateList2Kafka( GmallConstants.KAFKA_TOPIC_USER);
        }

    }


    private void  rowDateList2Kafka(String kafkaTopic){
            for (CanalEntry.RowData rowData : rowDataList) {
                List<CanalEntry.Column> columnsList = rowData.getAfterColumnsList();
                JSONObject jsonObject = new JSONObject();
                for (CanalEntry.Column column : columnsList) {

                    System.out.println(column.getName()+"::::"+column.getValue());
                    jsonObject.put(column.getName(),column.getValue());
                }

                MyKafkaSender.send(kafkaTopic,jsonObject.toJSONString());
            }

    }
}
