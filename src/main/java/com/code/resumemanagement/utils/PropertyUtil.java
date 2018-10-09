package com.code.resumemanagement.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/11/12.
 */
public class PropertyUtil {
    private static final Logger LOG = LoggerFactory.getLogger(PropertyUtil.class);
    private static Properties props;

    static {
        loadProps();
    }

    synchronized static private void loadProps() {
        LOG.debug("开始加载properties文件内容.......");
        props = new Properties();
        InputStream in = null;
        try {
        //第一种，通过类加载器进行获取properties文件流
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("config/ftp.properties");
        //第二种，通过类进行获取properties文件流
            //in = PropertyUtil.class.getResourceAsStream("config/ftp.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            LOG.error("ftp.properties文件未找到");
        } catch (IOException e) {
            LOG.error("出现IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                LOG.error("ftp.properties文件流关闭出现异常");
            }
        }
        LOG.debug("加载properties文件内容完成...........");
        LOG.debug("properties文件内容：" + props);
    }

    public static String getProperty(String key) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
