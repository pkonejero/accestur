import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"secom.accestur.front","secom.accestur.core"})
@EnableJpaRepositories
public class Application {

	public static void main(final String... args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
	}
}