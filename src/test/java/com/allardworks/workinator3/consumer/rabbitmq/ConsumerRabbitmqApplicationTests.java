package com.allardworks.workinator3.consumer.rabbitmq;

import com.allardworks.workinator3.consumer.rabbitmq.testsupport.RabbitMqTester;
import com.rabbitmq.client.Connection;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerRabbitmqApplicationTests {
	@Autowired private Connection connection;

	@Test
	public void contextLoads() {
		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
	}

	@Test
	@Autowired
	public void boo() throws Exception {
		try (val tester = new RabbitMqTester(connection)) {
			tester.
					declareTopic("a")
					.declareQueue("aa", "a", "a")
					.declareQueue("bb", "a", "b")
					.publish("a", "a", "queue a")
					.publish("a", "b", "queue b");
			System.out.println();
		}
	}
}
