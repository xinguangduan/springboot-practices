package org.practices.demo.client.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class TransferConfiguration {
    private String url = "https://pvtcui.lenovo.com.cn/v20180430/events";
    private String authorization;
}
