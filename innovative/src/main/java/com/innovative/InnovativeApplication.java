
package com.innovative;

import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan(basePackages = "com.innovative")
public class InnovativeApplication {

	public static void main(String[] args)  {
		SpringApplication.run(InnovativeApplication.class, args);
	} 
	/**
	 * 设置session 的有效期，
	 * @return
	 */
	 @Bean
	 public EmbeddedServletContainerCustomizer containerCustomizer(){
	        return new EmbeddedServletContainerCustomizer() {
	            @Override
	            public void customize(ConfigurableEmbeddedServletContainer container) {
	                 container.setSessionTimeout(1800);//单位为S session失效时间是30分钟
	           }
	     };
	 }

  /*打war包时使用，然后将pom.xml中的jar改成war, 此类需要继承SpringBootServletInitializer*/
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(InnovativeApplication.class);
//	}
}
