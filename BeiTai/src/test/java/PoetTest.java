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

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
public void testFindAllPoetsWithPagination1() {
    // 设置查询的页码为第三页，每页显示的诗人数量为5
    int page = 2;
    int size = 5;

    // 直接在查询前设置分页参数
    //PageHelper.startPage(page, size);
    // 执行查询
    List<Poet> poets = poetService.findAllPoets(page,size);

    PageInfo<Poet> pageInfo = new PageInfo<>(poets);
    System.out.println("总条数：" + pageInfo.getTotal());
    System.out.println("总页数：" + pageInfo.getPages());
    System.out.println("当前页：" + pageInfo.getPageNum());
    System.out.println("每页数量：" + pageInfo.getPageSize());
    System.out.println("是否第一页：" + pageInfo.isIsFirstPage());
    System.out.println("是否最后一页：" + pageInfo.isIsLastPage());

    // 打印诗人列表
    System.out.println("Poets on page " + page + ":");
    for (Poet poet : poets) {
        System.out.println(poet);
    }
}

    @Test
    public void testInsertPoet1() {
        Poet poet = new Poet();
        poet.setName("引用与");
        poet.setBirthDate(java.sql.Date.valueOf(LocalDate.of(2004, 1, 1)));
        poet.setDeathDate(java.sql.Date.valueOf(LocalDate.of(0000, 1, 1)));
        poet.setDynasty("新");
        poet.setBiography("21世纪伟大领袖诗人");


    }
}