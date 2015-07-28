package cn.iyowei.stockai.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * application.properties
 *
 * @author - Vick.liu (vicklin123@gmail.com)
 */
public class AppPropsReader {
    private static final String PROP_FILE_PATH = "/application.properties";
    protected static Logger logger = LoggerFactory.getLogger(AppPropsReader.class);
    private static Properties props;

    private static AppPropsReader reader;

    private AppPropsReader() {
        Resource resource = new ClassPathResource(PROP_FILE_PATH);
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static AppPropsReader newInstance() {
        if (null == reader) {
            reader = new AppPropsReader();
        }
        return reader;
    }

    public static Object get(String key) {
        newInstance();
        return AppPropsReader.props.get(key);
    }

    /**
     * 返回trim()后的string
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return get(key).toString().trim();
    }

}
