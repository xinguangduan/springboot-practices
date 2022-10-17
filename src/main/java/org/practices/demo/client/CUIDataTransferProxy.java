package org.practices.demo.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.practices.demo.client.entity.DataTransferReq;
import org.practices.demo.client.entity.DataTransferRsp;
import org.synchronoss.cloud.nio.multipart.BlockingIOAdapter;
import org.synchronoss.cloud.nio.multipart.MultipartContext;
import org.synchronoss.cloud.nio.multipart.util.IOUtils;
import org.synchronoss.cloud.nio.multipart.util.collect.CloseableIterator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.synchronoss.cloud.nio.multipart.Multipart.multipart;

@Slf4j
public class CUIDataTransferProxy{



    protected List<DataTransferRsp> execRequest(DataTransferReq req) {
        long startTime = System.currentTimeMillis();
        List<DataTransferRsp> respList = new ArrayList<>();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost httpPost = new HttpPost(req.getConfiguration().getUrl());
            StringBody userBody = new StringBody(req.getReqJson().toJSONString(), ContentType.APPLICATION_JSON);
            // build header
            httpPost.setHeader("authorization", req.getConfiguration().getAuthorization());
            httpPost.setHeader("connection", "Keep-Alive");
            httpPost.setHeader("accept", "*/*");

            // build body
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.addPart("body", userBody);
            HttpEntity entity = entityBuilder.build();
            httpPost.setEntity(entity);

            HttpResponse response = client.execute(httpPost);
            System.out.println(response.getProtocolVersion());
            HttpEntity responseEntity = response.getEntity();
            System.out.println(responseEntity.getContentType());

            DataTransferRsp resp = transfer(responseEntity);
            if (resp != null) {
                respList.add(resp);
            }
        } catch (IOException e) {
            log.error("", e);
        } finally {
            log.info("cost:{}", (System.currentTimeMillis() - startTime));
        }
        return respList;
    }


    private DataTransferRsp transfer(HttpEntity responseEntity) {
        DataTransferRsp resp = DataTransferRsp.builder().build();
        try {
            String contentType = responseEntity.getContentType().getValue();
            int contentLength = Integer.parseInt(responseEntity.getContentLength() + "");
            String contentEncoding = StandardCharsets.UTF_8.displayName();

            if (contentType.contains("application/json")) {
                String json = IOUtils.inputStreamAsString(responseEntity.getContent(), contentEncoding);
                JSONObject respJson = (JSONObject) JSON.parse(json);
                log.info(json);
                resp.setRspJson(respJson);

            } else {
                MultipartContext context = new MultipartContext(contentType, contentLength, contentEncoding);
                InputStream inputStream = responseEntity.getContent();
                CloseableIterator<BlockingIOAdapter.ParserToken> parts = multipart(context).forBlockingIO(inputStream);
                while (parts.hasNext()) {
                    BlockingIOAdapter.ParserToken partToken = parts.next();
                    switch (partToken.getType()) {
                        case PART:
                            BlockingIOAdapter.Part part = (BlockingIOAdapter.Part) partToken;
                            final Map<String, List<String>> headers = part.getHeaders();
                            List<String> header = headers.get("content-type");
                            if (header.contains("application/json; charset=UTF-8;")) {
                                final InputStream body = part.getPartBody();
                                String json = IOUtils.inputStreamAsString(body, contentEncoding);
                                JSONObject respJson = (JSONObject) JSONObject.parse(json);
                                log.info(json);
                                resp.setRspJson(respJson);
                            } else {
                                final InputStream binBody = part.getPartBody();
                                byte[] binByte = read(binBody);
                                log.info("binlen:{}", binByte.length);
                                resp.setRspBin(binByte);
                            }
                            break;
                        case NESTED_START:
                            // A marker to keep track of nested multipart and it gives access to the headers...
                            BlockingIOAdapter.NestedStart nestedStart = (BlockingIOAdapter.NestedStart) partToken;
                            final Map<String, List<String>> headerss = nestedStart.getHeaders();
                            break;
                        case NESTED_END:
                            // Just a marker but it might be used to keep track of nested multipart...
                            log.info("end part");
                            break;
                        default:
                            break;// Impossible
                    }
                }
            }
//        CloseableIterator<BlockingIOAdapter.ParserToken> parts1 = multipart(context)
//                .withBufferSize(500)
//                .withHeadersSizeLimit(16000)
//                .withMaxMemoryUsagePerBodyPart(100)
//                .forBlockingIO(inputStream);
//
//        CloseableIterator<BlockingIOAdapter.ParserToken> parts3 = multipart(context)
//                .withBufferSize(500)
//                .withHeadersSizeLimit(16000)
//                .withMaxMemoryUsagePerBodyPart(100)
//                .usePartBodyStreamStorageFactory(partBodyStreamStorageFactory)
//                .forBlockingIO(inputStream);

        } catch (Exception e) {
            log.error("transfer", e);
            return null;
        }
        return resp;
    }

    public byte[] read(final InputStream inputStream) {
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            int ch;
            while ((ch = inputStream.read()) >= 0) {
                buf.write(ch);
//                if (ch == '\n') { // be tolerant (RFC-2616 Section 19.3)
//                    break;
//                }
            }
            if (buf.size() == 0) {
                return null;
            }
            return buf.toByteArray();

        } catch (Exception e) {
            throw new IllegalStateException("Error reading the headers line", e);
        }
    }
}
