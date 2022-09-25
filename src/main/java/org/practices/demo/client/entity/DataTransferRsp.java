package org.practices.demo.client.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class DataTransferRsp {
    private JSONObject rspJson;
    private byte[] rspBin;
}
