package com.acceval.msexample.example;

import java.util.Optional;
import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<ResponseWrapper<ExampleDTO>> add(@RequestBody ExampleDTO example) {
		if (example == null) {
			return ResponseEntity.badRequest().build();
		}

		ExampleDTO myExample = example;
		try {
			myExample = service.addExample(example);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return ResponseWrapper.exceptionResponse(ex, myExample);
		}

		return ResponseEntity.ok(new ResponseWrapper<>(myExample));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<ResponseWrapper<ExampleDTO>> get(@PathVariable long id) {
		return service.getExample(id).map(ResponseWrapper::ok)
				.orElseGet(ResponseWrapper::notFound);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<ResponseWrapper<ExampleDTO>> update(@PathVariable long id, @RequestBody ExampleDTO example) {
		if (example == null) {
			return ResponseEntity.badRequest().build();
		}

		try {
			Optional<ExampleDTO> ex = service.updateExample(id, example);
			return ex.map(ResponseWrapper::ok)
					.orElseGet(ResponseWrapper::notFound);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return ResponseWrapper.exceptionResponse(ex, example);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<ResponseWrapper<Void>> delete(@PathVariable long[] ids) {
		try {
			service.deleteExamples(ids);
			return ResponseWrapper.ok();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return ResponseWrapper.exceptionResponse(ex);
		}
	}
}
