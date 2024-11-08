package clover.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * SpringMVC配置类 第2种方式
 * 替换之前的配置类MvcConfig
 */
@EnableWebMvc //1 启用json转换器
@Configuration
@ComponentScan("clover")
public class SpringMvcConfig implements WebMvcConfigurer {

    //开启静态资源处理
    @Override
    //当前这个方法继承自接口或父类
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


/**
 * 配置MappingJackson2HttpMessageConverter，用于处理HTTP消息转换。
 * 主要配置ObjectMapper实例，以自定义日期格式。
 *
 * @return 返回配置好的MappingJackson2HttpMessageConverter实例
 */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        // 创建ObjectMapper实例
        ObjectMapper objectMapper = new ObjectMapper();
        // 配置ObjectMapper，不将日期时间序列化为时间戳
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 设置日期格式为"yyyy-MM-dd"
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        // 返回配置好的MappingJackson2HttpMessageConverter实例
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }


    // 重写configureMessageConverters方法，用于配置消息转换器
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 向消息转换器列表中添加Jackson的Http消息转换器，用于处理JSON格式的数据
        converters.add(mappingJackson2HttpMessageConverter());
    }

}
