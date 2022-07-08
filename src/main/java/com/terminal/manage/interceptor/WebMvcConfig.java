package com.terminal.manage.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.method.support.CompositeUriComponentsContributor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.function.support.HandlerFunctionAdapter;
import org.springframework.web.servlet.function.support.RouterFunctionMapping;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

@Component
public class WebMvcConfig extends WebMvcConfigurationSupport {

    private final Logger log = LoggerFactory.getLogger(Interceptor.class);

    @Autowired
    private Interceptor interceptor;

    public WebMvcConfig() {
        super();
        log.info("初始化配置");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        super.setApplicationContext(applicationContext);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        super.setServletContext(servletContext);
    }

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping(ContentNegotiationManager contentNegotiationManager, FormattingConversionService conversionService, ResourceUrlProvider resourceUrlProvider) {
        return super.requestMappingHandlerMapping(contentNegotiationManager, conversionService, resourceUrlProvider);
    }

    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return super.createRequestMappingHandlerMapping();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("注册拦截器...............");
        registry.addInterceptor(interceptor).excludePathPatterns("/login");
        super.addInterceptors(registry);
    }

    @Override
    protected PathMatchConfigurer getPathMatchConfigurer() {
        return super.getPathMatchConfigurer();
    }

    @Override
    protected void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
    }

    @Override
    public PathMatcher mvcPathMatcher() {
        return super.mvcPathMatcher();
    }

    @Override
    public UrlPathHelper mvcUrlPathHelper() {
        return super.mvcUrlPathHelper();
    }

    @Override
    public ContentNegotiationManager mvcContentNegotiationManager() {
        return super.mvcContentNegotiationManager();
    }

    @Override
    protected Map<String, MediaType> getDefaultMediaTypes() {
        return super.getDefaultMediaTypes();
    }

    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        super.configureContentNegotiation(configurer);
    }

    @Override
    public HandlerMapping viewControllerHandlerMapping(PathMatcher pathMatcher, UrlPathHelper urlPathHelper, FormattingConversionService conversionService, ResourceUrlProvider resourceUrlProvider) {
        return super.viewControllerHandlerMapping(pathMatcher, urlPathHelper, conversionService, resourceUrlProvider);
    }

    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
    }

    @Override
    public BeanNameUrlHandlerMapping beanNameHandlerMapping(FormattingConversionService conversionService, ResourceUrlProvider resourceUrlProvider) {
        return super.beanNameHandlerMapping(conversionService, resourceUrlProvider);
    }

    @Override
    public RouterFunctionMapping routerFunctionMapping(FormattingConversionService conversionService, ResourceUrlProvider resourceUrlProvider) {
        return super.routerFunctionMapping(conversionService, resourceUrlProvider);
    }

    @Override
    public HandlerMapping resourceHandlerMapping(UrlPathHelper urlPathHelper, PathMatcher pathMatcher, ContentNegotiationManager contentNegotiationManager, FormattingConversionService conversionService, ResourceUrlProvider resourceUrlProvider) {
        return super.resourceHandlerMapping(urlPathHelper, pathMatcher, contentNegotiationManager, conversionService, resourceUrlProvider);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
    }

    @Override
    public ResourceUrlProvider mvcResourceUrlProvider() {
        return super.mvcResourceUrlProvider();
    }

    @Override
    public HandlerMapping defaultServletHandlerMapping() {
        return super.defaultServletHandlerMapping();
    }

    @Override
    protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        super.configureDefaultServletHandling(configurer);
    }

    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(ContentNegotiationManager contentNegotiationManager, FormattingConversionService conversionService, Validator validator) {
        return super.requestMappingHandlerAdapter(contentNegotiationManager, conversionService, validator);
    }

    @Override
    protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
        return super.createRequestMappingHandlerAdapter();
    }

    @Override
    public HandlerFunctionAdapter handlerFunctionAdapter() {
        return super.handlerFunctionAdapter();
    }

    @Override
    protected ConfigurableWebBindingInitializer getConfigurableWebBindingInitializer(FormattingConversionService mvcConversionService, Validator mvcValidator) {
        return super.getConfigurableWebBindingInitializer(mvcConversionService, mvcValidator);
    }

    @Override
    protected MessageCodesResolver getMessageCodesResolver() {
        return super.getMessageCodesResolver();
    }

    @Override
    protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        super.configureAsyncSupport(configurer);
    }

    @Override
    public FormattingConversionService mvcConversionService() {
        return super.mvcConversionService();
    }

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
    }

    @Override
    public Validator mvcValidator() {
        return super.mvcValidator();
    }

    @Override
    protected Validator getValidator() {
        return super.getValidator();
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    protected void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.extendMessageConverters(converters);
    }

    @Override
    public CompositeUriComponentsContributor mvcUriComponentsContributor(FormattingConversionService conversionService, RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        return super.mvcUriComponentsContributor(conversionService, requestMappingHandlerAdapter);
    }

    @Override
    public HttpRequestHandlerAdapter httpRequestHandlerAdapter() {
        return super.httpRequestHandlerAdapter();
    }

    @Override
    public SimpleControllerHandlerAdapter simpleControllerHandlerAdapter() {
        return super.simpleControllerHandlerAdapter();
    }

    @Override
    public HandlerExceptionResolver handlerExceptionResolver(ContentNegotiationManager contentNegotiationManager) {
        return super.handlerExceptionResolver(contentNegotiationManager);
    }

    @Override
    protected void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

    @Override
    protected void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.extendHandlerExceptionResolvers(exceptionResolvers);
    }

    @Override
    protected ExceptionHandlerExceptionResolver createExceptionHandlerExceptionResolver() {
        return super.createExceptionHandlerExceptionResolver();
    }

    @Override
    public ViewResolver mvcViewResolver(ContentNegotiationManager contentNegotiationManager) {
        return super.mvcViewResolver(contentNegotiationManager);
    }

    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
    }

    @Override
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return super.mvcHandlerMappingIntrospector();
    }
}
