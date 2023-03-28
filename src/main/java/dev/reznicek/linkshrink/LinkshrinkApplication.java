package dev.reznicek.linkshrink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "dev.reznicek.linkshrink")
@EnableJpaRepositories(basePackages = "dev.reznicek.linkshrink.repository")
@EntityScan(basePackages = "dev.reznicek.linkshrink.model.entity")
@ComponentScan(basePackages = "dev.reznicek.linkshrink.service, dev.reznicek.linkshrink.bo")
public class LinkshrinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkshrinkApplication.class, args);
	}

}
