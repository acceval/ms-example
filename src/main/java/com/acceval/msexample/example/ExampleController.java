package com.acceval.msexample.example;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acceval.core.microservice.ExceptionHandler;
import com.acceval.core.microservice.model.ResponseWrapper;
import com.acceval.core.repository.QueryResult;
import lombok.RequiredArgsConstructor;

/**
 * Example rest controller for example process
 *
 * @author Julian
 */
@RestController
@RequestMapping("/example")
@RequiredArgsConstructor
public class ExampleController {
	private static final Logger log = LoggerFactory.getLogger(ExampleController.class);
	private final ExampleService service;

	@RequestMapping(method = RequestMethod.GET, value = "/search")
	public QueryResult search(@RequestParam @Nonnull MultiValueMap<String, String> mapParam) {
		return service.query(mapParam);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ResponseWrapper> add(@RequestBody Example example) {
		if (example == null) {
			return ResponseEntity.badRequest().build();
		}

		Example myExample = example;
		try {
			myExample = service.addExample(example);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return ExceptionHandler.buildExceptionResponse(myExample, ex);
		}

		return ResponseEntity.ok(new ResponseWrapper(myExample));
	}
}
