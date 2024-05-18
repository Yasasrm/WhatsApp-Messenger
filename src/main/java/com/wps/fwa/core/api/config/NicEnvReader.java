package com.wps.fwa.core.api.config;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Component
public class NicEnvReader {
    public static String getProperty(String value) throws IOException {
        String nicEnv = null;
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = NicEnvReader.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(input);
            nicEnv = properties.getProperty(value);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null)
                input.close();
        }
        return nicEnv;
    }
}
