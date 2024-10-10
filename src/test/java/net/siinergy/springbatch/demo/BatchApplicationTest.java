package net.siinergy.springbatch.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = { Application.class, JdbcConfiguration.class}, properties = { "spring.main.allow-bean-definition-overriding=true" })
@ActiveProfiles({ "TEST" })
public class BatchApplicationTest {

	@Test
	public void testContextLoads() {
		assertThat(true).isEqualTo(true);
	}
//
}
