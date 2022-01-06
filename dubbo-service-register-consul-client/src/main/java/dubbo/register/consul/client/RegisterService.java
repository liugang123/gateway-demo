package dubbo.register.consul.client;

import dubbo.register.consul.client.model.MetaDataRegisterDTO;

/**
 * 服务注册接口规范
 *
 * @author liugang
 * @create 2022/1/4
 */
public interface RegisterService {

    /**
     * 根据元数据注册服务
     *
     * @param metadata
     */
    void registerInterface(MetaDataRegisterDTO metadata);
}
