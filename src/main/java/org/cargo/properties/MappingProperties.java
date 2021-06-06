package org.cargo.properties;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MappingProperties {
    private static final Logger LOGGER = Logger.getLogger(MappingProperties.class);

    private static MappingProperties instance = null;

    private Properties properties;
    private static String propertyFileName = "mapping.properties";

    private MappingProperties(){
        LOGGER.info("Initializing MappingProperties class");

        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);

        try {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                LOGGER.error("Mapping properties file not found on the classpath");
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public static synchronized MappingProperties getInstance() {
        if (instance == null) {
            instance = new MappingProperties();
        }

        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
