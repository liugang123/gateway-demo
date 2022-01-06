package gateway.demo.filter.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author liugang
 * @create 2021/12/30
 */
@Slf4j
@Component
public class DubboGatewayFilterFactory extends AbstractGatewayFilterFactory<DubboGatewayFilterFactory.Config> {

    public DubboGatewayFilterFactory() {
        super(DubboGatewayFilterFactory.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.defer(() -> {
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                String body = "{\"name\":\"zhangsan\"}";
                log.info(">>>>>> dubboFilter running");
                return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(body.getBytes())));
            }));
        };
    }

    public static class Config {

    }
}
