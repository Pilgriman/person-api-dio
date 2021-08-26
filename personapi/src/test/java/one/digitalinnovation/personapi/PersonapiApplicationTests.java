package one.digitalinnovation.personapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class PersonapiApplicationTests {

	@Test
	void contextLoads(){
		assertDoesNotThrow(() ->   PersonapiApplication.main(new String[]{}));
	}

}



