package site.katchup.katchupserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class KatchupserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(KatchupserverApplication.class, args);
	}

}
