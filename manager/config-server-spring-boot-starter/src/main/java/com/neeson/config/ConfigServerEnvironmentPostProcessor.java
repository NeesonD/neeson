package com.neeson.config;

import com.neeson.config.env.CustomKeyValue;
import com.neeson.config.env.CustomPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.HashMap;

/**
 * @author neeson
 */
public class ConfigServerEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    public static final int DEFAULT_ORDER = Ordered.HIGHEST_PRECEDENCE + 6;

    private int order = DEFAULT_ORDER;

    /**
     * 这里可以从远程获取 customProperties
     * @param environment
     * @param application
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        HashMap<String, Object> customProperties = new HashMap<>();
        environment.getPropertySources().addFirst(new CustomPropertySource(new CustomKeyValue(customProperties)));
    }

    @Override
    public int getOrder() {
        return order;
    }
}
