package org.example.dubbo.provider;

import dubbo.register.consul.client.annotation.DubboServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.dubbo.api.DemoService;
import org.springframework.stereotype.Service;


/**
 * @author liugang
 * @create 2021/12/24
 */
@DubboService
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @DubboServiceClient(path = "/demo/method")
    public String method(String param) {
        log.info("调用dubbo服务，param:{}", param);
        return param;
    }
}
