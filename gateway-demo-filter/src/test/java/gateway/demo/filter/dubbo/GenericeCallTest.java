package gateway.demo.filter.dubbo;

import dubbo.register.consul.client.ConsulClientRegister;
import dubbo.register.consul.client.model.MetaDataRegisterDTO;
import gateway.demo.filter.BaseTest;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.Assert;
import org.junit.Test;

/**
 * dubbo泛化调用
 *
 * @author liugang
 * @create 2021/12/28
 */
public class GenericeCallTest extends BaseTest {

    @Test
    public void demoServiceTest() {

        // 1.注册中心信息
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("spring-cloud://localhost");

        // 2.服务信息
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-service");
        applicationConfig.setRegistry(registryConfig);

        // 3.服务接口信息
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<GenericService>();
        referenceConfig.setInterface("org.example.dubbo.api.DemoService");
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setGeneric("true");
        referenceConfig.setTimeout(7000);
        referenceConfig.setCheck(false);
        referenceConfig.setProtocol("dubbo");

        // 4.远程代理
        GenericService genericService = referenceConfig.get();
        // 远程调用
        Object result = genericService.$invoke("method", new String[]{"java.lang.String"}, new Object[]{"param1111"});
        System.out.println("dubbo generic call result:" + result);
        Assert.assertNotNull(result);
    }

}
