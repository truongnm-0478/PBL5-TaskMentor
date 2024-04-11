package configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getRedisHost() {
        return properties.getProperty("redis.host");
    }

    public static String getRefreshTokenKeyPrefix() {
        return properties.getProperty("refresh.token.key.prefix");
    }

    public static String getSecretKey() { return properties.getProperty("access.token.key.prefix"); }
}
