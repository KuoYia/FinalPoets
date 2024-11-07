package clover.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration

@ComponentScan(basePackages = "clover") // 确保包含 com.clover.service.impl
@PropertySource("classpath:jdbc.properties")
@MapperScan("clover.dao")

public class SpringConfig {

    @Bean
    public DataSource dataSource(
            @Value("${jdbc.driver}") String driver,
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.username}") String username,
            @Value("${jdbc.password}") String password) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
       //这一句至关重要
        sessionFactory.setPlugins(new Interceptor[]{pageInterceptor()});

        // MyBatis会自动使用SLF4J与Logback的桥接，无需显式设置日志实现
        return sessionFactory.getObject();

    }

    @Configuration
    @EnableAspectJAutoProxy
    public class AopConfig {
        // 这个类不需要定义任何Bean，它的存在只是为了启用AOP
    }
    // 配置PageHelper的属性
    /**
     * 分页插件
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("params", "count=countSql");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

}