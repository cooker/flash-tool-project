package org.grant.springboot.magic;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * grant
 * 28/8/2019 6:01 PM
 * 描述：
 */
@ConfigurationProperties(
    prefix = "yaml.loader", ignoreInvalidFields = true
)
@Data
public class YamlLoaderProperties {
    private String files;
    private boolean enable;

}
