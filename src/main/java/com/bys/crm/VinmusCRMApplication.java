package com.bys.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration
@EnableWebMvc
@EnableAsync
@EnableScheduling
@ComponentScan({ "com.bys.*"})
@ImportResource("classpath:db-config.xml")
@PropertySource("classpath:properties/database-setting.properties")
@PropertySource("classpath:properties/email-setting.properties")
@EnableJpaRepositories
@EnableJpaAuditing
public class VinmusCRMApplication extends SpringBootServletInitializer {
	 
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VinmusCRMApplication.class);
    }
 
    public static void main(String[] args) {
        SpringApplication.run(VinmusCRMApplication.class, args);
    }
}


//public class VinmusCRMApplication {
//
//	public static void main(String[] args) throws Exception {
//	       SpringApplication.run(VinmusCRMApplication.class, args);    	
//	    }
//}
