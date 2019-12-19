package com.dyd.operation.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * spring-mvc配置类 用于对web服务做默认配置等优化
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	Environment env;
	
	@Resource
	private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
		if (viewResolver != null) {
			Map<String, Object> vars = new HashMap<>();
			vars.put("src", "/");
			viewResolver.setStaticVariables(vars);
		}
	}

	/**
	 * 
	 * 默认首页配置
	 * 
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("redirect:index");
		registry.addViewController("/main").setViewName("main");
		registry.addViewController("/403").setViewName("error/403");
		registry.addViewController("/404").setViewName("error/404");
		registry.addViewController("/405").setViewName("error/405");
		registry.addViewController("/500").setViewName("error/500");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		List<String> excludeList = new ArrayList<String>();
		excludeList.add("/error/**");
		excludeList.add("/login");
		registry.addInterceptor(new AccessCheckInterceptor()).addPathPatterns("/**").excludePathPatterns(excludeList);
		System.out.println("===========   拦截器注册完毕   ===========");
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        /**
         * 序列换成json时,将所有的long变成string
         * 因为js中得数字类型不能包含所有的java long值
         */
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(jackson2HttpMessageConverter);
    }
}
