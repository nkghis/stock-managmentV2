package ics.ci.stock.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")

//Class for use Application.propertie variable on code
public class ConfigProperties {

    private final Environment env;

    public ConfigProperties(Environment env) {
        this.env = env;
    }

    public String getConfigValue(String configKey){
        return env.getProperty(configKey);
    }
}
