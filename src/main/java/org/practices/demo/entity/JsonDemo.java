package org.practices.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.java.Log;

@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Log
public class JsonDemo {
    private String name;
    private String address;

    public static void main(String[] args) {
        JsonDemo json = JsonDemo.builder().address("beijing cahngping ").name("zhangsan ").build();
        log.info(json.toString());
        int cores = Runtime.getRuntime().availableProcessors();
        var acceptors = Math.max(1, Math.min(4, cores / 8));
        log.info("acceptors:"+acceptors);
    }
}
