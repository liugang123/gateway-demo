package dubbo.register.consul.client;

import com.ecwid.consul.v1.kv.KeyValueClient;
import dubbo.register.consul.client.model.MetaDataRegisterDTO;
import dubbo.register.consul.client.util.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;

/**
 * consul注册中心
 *
 * @author liugang
 * @create 2022/1/4
 */
public class ConsulClientRegister implements RegisterService {

    @Autowired
    private ConsulRegistration consulRegistration;

    @Autowired
    private KeyValueClient keyValueClient;

    /**
     * 注册服务方法
     *
     * @param metadata
     */
    @Override
    public void registerInterface(MetaDataRegisterDTO metadata) {
        String rpcType = metadata.getRpcType();
        String contextPath = metadata.getContextPath().substring(1);
        this.registerMetaData(rpcType, contextPath, metadata);
    }

    /**
     * 注册接口原数据消息
     *
     * @param rpcType
     * @param contextPath
     * @param metadata
     */
    private void registerMetaData(String rpcType, String contextPath, MetaDataRegisterDTO metadata) {
        String metadataNodeName = this.buildMetadataNodeName(metadata);
        String metadataPath = String.join("/", "metadata", rpcType, contextPath);
        String realNode = String.join("/", metadataPath, metadataNodeName);
        String metadataJson = GsonUtils.getInstance().toJson(realNode);
        keyValueClient.setKVValue(realNode, metadataJson);
    }

    /**
     * 服务节点名称
     *
     * @param metadata
     * @return
     */
    private String buildMetadataNodeName(MetaDataRegisterDTO metadata) {
        String nodeName = metadata.getServiceName() + "." + metadata.getMethodName();
        return nodeName.startsWith("/") ? nodeName.substring(1) : nodeName;
    }

}
