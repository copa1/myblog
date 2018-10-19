package com.copa.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 主页设置、定制错误页面
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    // 获取配置文件中图片的路径（临时），方便本地测试
   /* @Value("${cbs.imagesPath}")
    private String mImagesPath;*/


    // 访问图片方法（临时），方便本地测试
   /* @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (mImagesPath.equals("") || mImagesPath.equals("${cbs.imagesPath}")) {
            String imagesPath = WebMvcConfig.class.getClassLoader().getResource("").getPath();
            if (imagesPath.indexOf(".jar") > 0) {
                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
            } else if (imagesPath.indexOf("classes") > 0) {
                imagesPath = "file:" + imagesPath.substring(0, imagesPath.indexOf("classes"));
            }
            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/")) + "/images/";
            mImagesPath = imagesPath;
        }
        LoggerFactory.getLogger(WebMvcConfig.class).info("imagesPath=" + mImagesPath);
        registry.addResourceHandler("/images/**").addResourceLocations(mImagesPath);
        super.addResourceHandlers(registry);
    }*/

    //主页设置
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    //定制错误页面
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
                ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");

                container.addErrorPages(error401Page,error404Page,error403Page,error500Page);
            }
        };
    }
}
