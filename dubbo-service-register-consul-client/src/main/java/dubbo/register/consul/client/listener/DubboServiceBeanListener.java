package dubbo.register.consul.client.listener;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import dubbo.register.consul.client.RegisterService;
import dubbo.register.consul.client.annotation.DubboServiceClient;
import dubbo.register.consul.client.model.MetaDataRegisterDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.ServiceBean;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * dubbo服务类监听
 *
 * @author liugang
 * @create 2021/12/31
 */
@Slf4j
public class DubboServiceBeanListener implements ApplicationListener<ContextRefreshedEvent> {

    private AtomicBoolean registered = new AtomicBoolean(false);

    private final ExecutorService executorService;

    @Autowired
    private RegisterService consulClientRegister;

    private String contextPath = "/";

    private String appName;

    private String host;

    private String port;

    public DubboServiceBeanListener() {
        executorService = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNamePrefix("dubbo-service-consul-client-thread-pool").build());
    }

    /**
     * spring容器刷新事件
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //executorService.execute(() -> {
        Map<String, ServiceBean> map = event.getApplicationContext().getBeansOfType(ServiceBean.class);
        for (Map.Entry<String, ServiceBean> entry : map.entrySet()) {
            handlerServiceBean(entry.getValue());
        }
        //});
    }

    /**
     * 处理dubbo service
     *
     * @param serviceBean
     */
    private void handlerServiceBean(ServiceBean serviceBean) {
        Object refProxy = serviceBean.getRef();
        Class<?> clazz = refProxy.getClass();
        if (AopUtils.isAopProxy(refProxy)) {
            clazz = AopUtils.getTargetClass(refProxy);
        }
        Method[] methods = ReflectionUtils.getUniqueDeclaredMethods(clazz);
        for (Method method : methods) {
            DubboServiceClient dubboServiceClient = method.getAnnotation(DubboServiceClient.class);
            if (Objects.nonNull(dubboServiceClient)) {
                log.info("注册dubbo service --> :{} ", method.getName());
                MetaDataRegisterDTO metaDataRegisterDTO = this.buildMetaDataDTO(serviceBean, dubboServiceClient, method);
                this.registerService(metaDataRegisterDTO);
            }
        }
    }

    /**
     * 构造服务元数据
     *
     * @param serviceBean 服务实例
     * @param method      方法对象
     * @return
     */
    private MetaDataRegisterDTO buildMetaDataDTO(ServiceBean serviceBean, DubboServiceClient dubboServiceClient, Method method) {
        String appName = this.appName;
        if (StringUtils.isEmpty(appName)) {
            appName = serviceBean.getApplication().getName();
        }
        String serviceName = serviceBean.getInterface();
        String path = dubboServiceClient.path();
        String host = NetUtil.getLocalhost().getHostAddress();
        int port = StringUtils.isEmpty(this.port) ? -1 : Integer.parseInt(this.port);
        String methodName = method.getName();
        Class<?>[] parameterTypesClazz = method.getParameterTypes();
        String parameterTypes = Arrays.stream(parameterTypesClazz).map(m -> m.getName()).collect(Collectors.joining(","));
        return MetaDataRegisterDTO.builder()
                .appName(appName)
                .serviceName(serviceName)
                .methodName(methodName)
                .contextPath(contextPath)
                .host(host)
                .port(port)
                .path(path)
                .parameterTypes(parameterTypes)
                .rpcType("dubbo")
                .build();
    }

    /**
     * 注册dubbo服务
     *
     * @param metaDataRegisterDTO 注册服务元数据
     */
    private void registerService(MetaDataRegisterDTO metaDataRegisterDTO) {
        consulClientRegister.registerInterface(metaDataRegisterDTO);
    }
}
