package com.ndptest.tool;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ToolApplicationTests {
	@Autowired
	private ResultRepository resultRepository;

	@Test
	void testJpa() {
//		IntStream.rangeClosed(1, 10).forEach(i ->{
//			Result result = Result.builder()
//					.number(String.valueOf(i))
//					.selectType(i)
//					.username("testUser" + String.valueOf(i))
//					.responseTime("300ms")
//					.build();
//			resultRepository.save(result);
//		});
		resultRepository.deleteAll();

		List<Result> result = this.resultRepository.findAll();
		System.out.println(result);
	}

}
