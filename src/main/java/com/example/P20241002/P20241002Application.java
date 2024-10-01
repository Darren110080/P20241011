package com.example.P20241002;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.systex.filter.AuthFilter;

@SpringBootApplication(scanBasePackages = {"com.systex.*", "com.example"})
@EnableJpaRepositories(basePackages = "com.systex.repository")
@EntityScan(basePackages = {"com.systex", "com.example","com.systex.model"}) // 指定實體類的包

//@SpringBootApplication(scanBasePackages = {"com.systex.*", "com.example"})
//@EnableJpaRepositories(basePackages = {"com.systex.repository"})
public class P20241002Application {

	public static void main(String[] args) {
		SpringApplication.run(P20241002Application.class, args);
	}
	
	
	@Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter());
        registrationBean.addUrlPatterns("/*"); // 应用到所有 URL
        return registrationBean;
    }

}
