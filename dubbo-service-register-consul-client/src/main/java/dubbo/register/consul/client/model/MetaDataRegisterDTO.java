package dubbo.register.consul.client.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 服务注册元数据
 *
 * @author liugang
 * @create 2022/1/4
 */
@Data
public class MetaDataRegisterDTO implements Serializable {

    private String appName;

    private String contextPath;

    private String path;

    private String pathDesc;

    private String rpcType;

    private String serviceName;

    private String methodName;

    private String ruleName;

    private String parameterTypes;

    private String rpcExt;

    private boolean enabled;

    private String host;

    private Integer port;

    private List<String> pluginNames;

    private boolean registerMetaData;

    public MetaDataRegisterDTO() {
    }

    public MetaDataRegisterDTO(final Builder builder) {
        this.appName = builder.appName;
        this.contextPath = builder.contextPath;
        this.path = builder.path;
        this.pathDesc = builder.pathDesc;
        this.rpcType = builder.rpcType;
        this.serviceName = builder.serviceName;
        this.methodName = builder.methodName;
        this.ruleName = builder.ruleName;
        this.parameterTypes = builder.parameterTypes;
        this.rpcExt = builder.rpcExt;
        this.enabled = builder.enabled;
        this.host = builder.host;
        this.port = builder.port;
        this.pluginNames = builder.pluginNames;
        this.registerMetaData = builder.registerMetaData;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Data
    public static final class Builder {
        private String appName;

        private String contextPath;

        private String path;

        private String pathDesc;

        private String rpcType;

        private String serviceName;

        private String methodName;

        private String ruleName;

        private String parameterTypes;

        private String rpcExt;

        private boolean enabled;

        private String host;

        private Integer port;

        private List<String> pluginNames = Collections.emptyList();

        private boolean registerMetaData;

        private Builder() {
        }

        /**
         * appName.
         *
         * @param appName appName
         * @return Builder
         */
        public Builder appName(final String appName) {
            this.appName = appName;
            return this;
        }

        /**
         * contextPath.
         *
         * @param contextPath contextPath
         * @return Builder
         */
        public Builder contextPath(final String contextPath) {
            this.contextPath = contextPath;
            return this;
        }

        /**
         * path.
         *
         * @param path path
         * @return Builder
         */
        public Builder path(final String path) {
            this.path = path;
            return this;
        }

        /**
         * pathDesc.
         *
         * @param pathDesc pathDesc
         * @return Builder
         */
        public Builder pathDesc(final String pathDesc) {
            this.pathDesc = pathDesc;
            return this;
        }

        /**
         * rpcType.
         *
         * @param rpcType rpcType
         * @return Builder
         */
        public Builder rpcType(final String rpcType) {
            this.rpcType = rpcType;
            return this;
        }

        /**
         * serviceName.
         *
         * @param serviceName serviceName
         * @return Builder
         */
        public Builder serviceName(final String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        /**
         * methodName.
         *
         * @param methodName methodName
         * @return Builder
         */
        public Builder methodName(final String methodName) {
            this.methodName = methodName;
            return this;
        }

        /**
         * ruleName.
         *
         * @param ruleName ruleName
         * @return Builder
         */
        public Builder ruleName(final String ruleName) {
            this.ruleName = ruleName;
            return this;
        }

        /**
         * parameterTypes.
         *
         * @param parameterTypes parameterTypes
         * @return Builder
         */
        public Builder parameterTypes(final String parameterTypes) {
            this.parameterTypes = parameterTypes;
            return this;
        }

        /**
         * rpcExt.
         *
         * @param rpcExt rpcExt
         * @return Builder
         */
        public Builder rpcExt(final String rpcExt) {
            this.rpcExt = rpcExt;
            return this;
        }

        /**
         * enabled.
         *
         * @param enabled enabled
         * @return Builder
         */
        public Builder enabled(final boolean enabled) {
            this.enabled = enabled;
            return this;
        }


        /**
         * appName.
         *
         * @param host host
         * @return Builder
         */
        public Builder host(final String host) {
            this.host = host;
            return this;
        }

        /**
         * port.
         *
         * @param port port
         * @return Builder
         */
        public Builder port(final Integer port) {
            this.port = port;
            return this;
        }

        /**
         * pluginNames.
         *
         * @param pluginNames pluginNames
         * @return Builder
         */
        public Builder pluginNames(final List<String> pluginNames) {
            this.pluginNames = pluginNames;
            return this;
        }

        /**
         * registerMetaData.
         *
         * @param registerMetaData registerMetaData
         * @return Builder
         */
        public Builder registerMetaData(final boolean registerMetaData) {
            this.registerMetaData = registerMetaData;
            return this;
        }

        /**
         * build.
         *
         * @return MetaDataRegisterDTO
         */
        public MetaDataRegisterDTO build() {
            return new MetaDataRegisterDTO(this);
        }
    }
}

