package dubbo.register.consul;

import dubbo.register.consul.client.ConsulClientRegister;
import dubbo.register.consul.client.model.MetaDataRegisterDTO;
import org.junit.Test;

/**
 * @author liugang
 * @create 2022/1/6
 */
public class RegistryClientTest extends  BaseTest {

    @Test
    public static void consulRegisterTest() {
        // 服务数据信息
        MetaDataRegisterDTO metaDataRegisterDTO = new MetaDataRegisterDTO();
        metaDataRegisterDTO.setParameterTypes("java.lang.String");
        metaDataRegisterDTO.setAppName("dubbo-service");
        metaDataRegisterDTO.setContextPath("/");
        metaDataRegisterDTO.setRegisterMetaData(false);
        metaDataRegisterDTO.setMethodName("method");
        metaDataRegisterDTO.setServiceName("org.example.dubbo.api.DemoService");
        metaDataRegisterDTO.setEnabled(false);
        metaDataRegisterDTO.setPath("/");
        metaDataRegisterDTO.setPort(-1);
        metaDataRegisterDTO.setHost("10.201.60.207");
        metaDataRegisterDTO.setRpcExt("dubbo");

        // 注册客户端
        ConsulClientRegister register = new ConsulClientRegister();

        // 注册服务
        register.registerInterface(metaDataRegisterDTO);

        System.out.println("register success");
    }

}
