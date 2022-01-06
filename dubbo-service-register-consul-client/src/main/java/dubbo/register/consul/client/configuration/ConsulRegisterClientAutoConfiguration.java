package dubbo.register.consul.client.configuration;

import dubbo.register.consul.client.ConsulClientRegister;
import dubbo.register.consul.client.RegisterService;
import dubbo.register.consul.client.listener.DubboServiceBeanListener;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.consul.ConsulAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liugang
 * @create 2022/1/4
 */
@Configuration
@AutoConfigureAfter(ConsulAutoConfiguration.class)
public class ConsulRegisterClientAutoConfiguration {

    @Bean
    public DubboServiceBeanListener dubboServiceBeanListener() {
        return new DubboServiceBeanListener();
    }

    @Bean
    public RegisterService consulClientRegister() {
        return new ConsulClientRegister();
    }

}
