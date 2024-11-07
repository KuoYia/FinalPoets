package clover.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcIniter extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
//        return new Class[]{MvcConfig.class};
        return new Class[]{SpringMvcConfig.class}; //替换之前的配置类MvcConfig

    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
