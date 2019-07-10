package com.sensing.core.utils.props;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * com.sensing.core.utils.props
 *
 * @author haowenfeng
 * @date 2018年6月26日 下午5:43:13
 */
@SuppressWarnings("all")
public class PropUtils {
    private static Logger log = LoggerFactory.getLogger(PropUtils.class);
    private static Properties PROP_CFG;

    private static Map prop;
    private static Map otherProp;

    /**
     * 默认 product生产环境
     */

    public static String ENV;

    static {
        String env = System.getProperty("spring.profiles.active");
        if (env == null) {
            PropUtils.ENV = "product";
        } else {
            PropUtils.ENV = env;
        }
        //设置log4j的配置文件
        PropertyConfigurator.configure(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "properties/" + ENV + "/log4j.properties");
    }

    /**
     * 框架配置文件名(properties)
     */
    private static String DEFAULT_FILE = "properties/" + ENV + "/remoteutil";
    private static String OPERATION_FILE = "properties/" + ENV + "/operation";
    private static String OTHER_FILE = "properties/" + ENV + "/jdbc";


    static {
        // 读取系统配置
        PROP_CFG = loadProperties("properties/" + ENV + "/remoteutil.properties");
        prop = getMap(DEFAULT_FILE);
        otherProp = getMap(OTHER_FILE);
    }

    private PropUtils() {
    }

    /**
     * @param proptName
     * @return
     * @Description 载入Properties文件
     */
    public static Properties loadProperties(String proptName) {
        Properties prop = new Properties();
        try {
            InputStream is = PropUtils.class.getClassLoader().getResourceAsStream(proptName);
            if (is != null && is.available() > 0) {
                prop.load(is);
            }
        } catch (IOException e) {
            log.error("读取配置文件异常：[{}]", proptName, e);
        }
        return prop;
    }

    public static void reloadProperties() {
        Properties prop = new Properties();
        try {

            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            InputStream is = new FileInputStream(path + "/" + "properties/" + ENV + "/remoteutil.properties");
            if (is != null && is.available() > 0) {
                prop.load(is);
            }
            PROP_CFG = prop;
        } catch (Exception e) {
            log.error("重载配置文件异常：[{}]", "properties/remoteutil.properties", e);
        }
    }

    /**
     * 以指定资源文件中所有参数名（key）与参数值（value）生成HashMap
     *
     * @param resourceFileName 资源文件名
     * @return hashmap created
     */
    public static HashMap getMap(String resourceFileName) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(resourceFileName);
            if (log.isDebugEnabled())
                log.debug("local language is [" + bundle.getLocale().getDisplayLanguage() + "]");
            java.util.Enumeration keys = bundle.getKeys();
            HashMap<String, String> map = new HashMap<String, String>();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                String value = bundle.getString(key);
                value = value == null ? "" : value.trim();
                map.put(key, value);
            }
            if (log.isInfoEnabled()) {
                log.info("SystemUtils: read "
                        + resourceFileName + ".properties");
            }
            return map;
        } catch (Throwable t) {
            log.error("SystemUtils: read "
                    + resourceFileName + ".properties error: " + t.getMessage());
            return new HashMap();
        }
    }

    /**
     * get key from sourceFile
     *
     * @param sourceFile
     * @param key
     * @return
     */
    public static String getConfig(String sourceFile, String key) {
        ResourceBundle bundle = ResourceBundle.getBundle(sourceFile);
        String value = bundle.getString(key);
        if (log.isInfoEnabled()) {
            log.info("Read config in " + sourceFile + " '" + key + "' = "
                    + value);
        }
        return value;
    }

    /**
     * get key from default sourceFile
     *
     * @param key
     * @return
     */
    public static String getConfigByMap(String key) {
        String value = (String) prop.get(key);
        if (value != null) {
            return value.trim();
        }
        value = (String) otherProp.get(key);
        if (value != null) {
            return value.trim();
        }
        return value;
    }

    public static String getConfigByMap(String key, String defaultValue) {
        String value = getConfigByMap(key);
        if (null != value && !"".equals(value)) {
            return value;
        }
        return defaultValue;
    }

    public static Integer getConfigByMapInt(String key, int defaultValue) {
        String value = getConfigByMap(key);
        if (null == value || "".equals(value)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.error("key=" + key + ",value=" + value + " parse int error! " + e.getMessage());
            return defaultValue;
        }
    }

    public static boolean getConfigByMapBoolean(String key, boolean defaultValue) {
        String value = getConfigByMap(key);
        if (null == value || "".equals(value)) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            log.error("key=" + key + ",value=" + value + " parse boolean error: " + e.getMessage());
            return defaultValue;
        }
    }

    public static String getConfigByFile(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle(DEFAULT_FILE);
        String value = bundle.getString(key);
        if (log.isInfoEnabled()) {
            log.info("Read config '" + key + "' = " + value);
        }
        if (value != null) {
            return value.trim();
        }
        return value;
    }

    /**
     * 根据配置文件config.properties的key获取value
     * 不存在key时返回null
     *
     * @return
     */
    public static String getString(String key) {
        return PROP_CFG.getProperty(key);
    }

    /**
     * 根据配置文件config.properties的key获取value
     * 不存在key时返回defaultVal
     *
     * @return
     */
    public static String getString(String key, String defaultVal) {
        String val = PROP_CFG.getProperty(key);
        return val == null ? defaultVal : val;
    }

    /**
     * 根据配置文件config.properties的key获取int类型value
     * 不存在key或不是int类型时返回0
     *
     * @return
     */
    public static int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * 根据配置文件config.properties的key获取value
     * 不存在key或不是int类型时返回defaultVal
     *
     * @return
     */
    public static int getInt(String key, int defaultVal) {
        String val = PROP_CFG.getProperty(key);
        if (StringUtils.isNotBlank(val)) {
            try {
                return Integer.parseInt(val);
            } catch (Exception e) {
                log.error("获取int类型配置信息异常，key：[{}] ", key, e);
            }
        }
        return defaultVal;
    }

    /**
     * 根据配置文件config.properties的key获取boolean类型value
     *
     * @return
     */
    public static boolean getBoolean(String key) {
        String value = getString(key);
        return Boolean.parseBoolean(value);
    }
}
