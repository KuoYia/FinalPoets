import clover.config.SpringConfig;
import clover.pojo.Poet;
import clover.service.impl.PoetServiceImpl;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.logging.LogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class PoetTest {

    private AnnotationConfigApplicationContext applicationContext;
    private PoetServiceImpl poetService;

    @BeforeEach
    public void setUp() {
        // 设置MyBatis日志实现为SLF4J
        LogFactory.useSlf4jLogging();

        // 创建Spring应用上下文
        applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        // 从应用上下文中获取PoetService的bean
        poetService = applicationContext.getBean(PoetServiceImpl.class);
    }

    @AfterEach
    public void tearDown() {
        // 关闭应用上下文
        applicationContext.close();
    }
    @Test
    public void testPoet2() {
        poetService.findById(5);
    }
    @Test
    public void testPoet1() {
        poetService.findById(5);
    }




    @Test
    public void testInsertPoet1() {
        Poet poet = new Poet();
        poet.setName("秦大恒");
        poet.setBirthDate(java.sql.Date.valueOf(LocalDate.of(2004, 1, 1)));
        poet.setDeathDate(java.sql.Date.valueOf(LocalDate.of(0000, 1, 1)));
        poet.setDynasty("新");
        poet.setBiography("21世纪伟大领袖诗人");


    }
}