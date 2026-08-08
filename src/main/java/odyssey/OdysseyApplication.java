package odyssey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/**
 * Application entry point.
 *
 * @SpringBootApplication is three annotations in one:
 *
 *   @Configuration        this class may define beans
 *   @EnableAutoConfiguration  configure things based on what is on the  classpath.
 *   @ComponentScan        find @RestController, @Service and friends in
 *                         this package and everything beneath it
 *
 * That last one is why every feature package lives under "odyssey".
 * A class outside this package tree would simply never be found.
 *
 * Each Spring AI capability gets its own package - chat, prompt,
 * structured, advisor, memory, and so on.
 */

@SpringBootApplication
public class OdysseyApplication {

	public static void main(String[] args) {

		SpringApplication.run(OdysseyApplication.class, args);

	}

}
