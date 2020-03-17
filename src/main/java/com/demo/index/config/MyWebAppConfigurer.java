package com.demo.index.config;
 

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
 
@Configuration
public class MyWebAppConfigurer 
  extends WebMvcConfigurerAdapter {
 
 @Override
 public void addResourceHandlers(ResourceHandlerRegistry registry) {
  registry.addResourceHandler("/files/**").addResourceLocations("file:/Data/files/");
  super.addResourceHandlers(registry);
 }
}