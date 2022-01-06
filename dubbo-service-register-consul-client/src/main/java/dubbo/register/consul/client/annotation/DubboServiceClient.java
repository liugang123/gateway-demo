package dubbo.register.consul.client.annotation;

import java.lang.annotation.*;

/**
 * @author liugang
 * @create 2022/1/4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DubboServiceClient {

    String path();
}
