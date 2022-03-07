package iss.edu.sg.RWorkshop13;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import iss.edu.sg.RWorkshop13.controller.AddressbookController;

@SpringBootTest
class RWorkshop13ApplicationTests {

	@Autowired
	private AddressbookController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
