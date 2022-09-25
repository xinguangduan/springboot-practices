package org.practices.demo.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.practices.demo.client.entity.DataTransferReq;
import org.practices.demo.client.entity.DataTransferRsp;
import org.practices.demo.client.entity.TransferConfiguration;

import java.util.List;

@Slf4j
public class DataTransferProxyTest {
    public static void main(String[] args) {
        //https://api-cui.naea1.uds.lenovo.com/v20180430/events
        TransferConfiguration tc = TransferConfiguration.builder()
                .url("https://pvtcui.lenovo.com.cn/v20180430/events")
                .authorization("Bearer eyJlbmMiOiJBMTI4R0NNIiwiYWxnIjoiZGlyIn0..zhrqTAwI1-BXoLyp.j4TxCgFcvtMpFX_V-AxKMP7R4arLqWk7G4NKb-M-JkHoUyh1tylePt73Pfc6bEcY-uWH4bONG3ZMbD27gDti3mws01JFyG0oUohe9Jx91PxkNhvDZCbESOt5O3gVog2mselIxWv8Fi9oR9d8566pKOuqJgxfw7fyT9TZXRJ8ZNNMXr3N2AxxaZtyt5ijUlRTZqMXXzqpd82qN68iG_EmEmjrCUA2-1Jh9VOxZu8Pf8PMVkw-0MxsetWboMjodAS8vdRS7_oAyBiZjmSMPlBUltip8roLYySZCbSHl-0ko-bBOtPJTxuaTRdR2IBsLE8GF-yx3AjoAv3HQ-miwr6aea0QQGEAK67F_az6DdPckSKXF6zWceMOBzjNCilEwdHJ5tF403JLCbTr4Hr1rdnLerSoW2_zaoGzUeRu-I6dIkLkkNedcehm6VniplFQDblEb_19avzqaPky3ElKGwPzghN7upA_Ns-EgeJoPM-bJDnJWY12oL5KTDVpvrxYUHZDOrRnmS93i-yl9PDqJrKo4CS6i_Nf6jmkXdq6qDFLCGFdNbvBDumZ6ZMPsVF2uTq-UjExvUHoRyoz3qvsrUU4iWU2ZrEGzQm2xdITfK3z6g5sOBgyOf4SXWZOlscrqUSP-L9Uj9nlSxxgUg-CthBAou7qGDJGtebbubJNMwBHaQzYKMLnQ8YAl7O_clYoSCXAU1uHUpP3VpTjuP9xISq5sukLB_PTNdf7k_Yg8pkVGX2dHnoUlqEMxd8BaM0WJnzjQx5xlTcgPl7bEi8Gjg5mPqKdLbPktgZAfEqNTcUjV5-u3xqKt8fncw99ydRqcfv7aeuq.pDkxIjaDxh8O5z6P69phuw")
                .build();

        JSONObject req = new JSONObject(true);

        final String requestData = "{" +
                "  \"event\": {" +
                "    \"header\": {" +
                "      \"namespace\": \"SpeechSynthesizer\"," +
                "      \"name\": \"RequestTTS\"," +
                "      \"messageId\": \"1e468714-2214-4135-bc1e-2b123d362613-NzE4MjM=12345\"," +
                "      \"dialogRequestId\": \"1f393af0-3914-4e31-8614-212727391437-12345\"" +
                "    }," +
                "    \"payload\": {" +
                "      \"format\": \"plainText\"," +
                "      \"content\": \"Chemelion is one of the most bizarre of all reptiles\"," +
                "      \"language\": \"en-US\"" +
                "    }" +
                "  }" +
                "}";

        req = (JSONObject) JSON.parse(requestData);

        DataTransferReq dataTransferReq = DataTransferReq.builder()
                .reqJson(req)
                .configuration(tc)
                .build();

        CUIDataTransferProxy transferProxy = new CUIDataTransferProxy();
        List<DataTransferRsp> respList = transferProxy.execRequest(dataTransferReq);

        for (int i = 0; i < respList.size(); i++) {
            DataTransferRsp rsp = respList.get(i);
            log.info("resp json:{}", rsp.getRspJson());
            log.info("resp bin:{}", rsp.getRspBin());

        }


    }
}
