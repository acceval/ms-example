package com.acceval.msexample.type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acceval.core.microservice.model.LabelValue;
import lombok.RequiredArgsConstructor;

/**
 * TODO: Document this
 *
 * @author Julian
 */
@RestController
@RequestMapping("/datasource")
@RequiredArgsConstructor
public class DataSourceController {
	private final TypeRepository repo;

	@RequestMapping(method = RequestMethod.GET, value = "/type")
	public List<LabelValue> getTypeDataSource(@RequestParam("q") String query) {
		return StreamSupport.stream(repo.findAll().spliterator(), false)
				.filter(type -> query == null || type.getLabel().toLowerCase().contains(query.toLowerCase()))
				.map(type -> new LabelValue(type.getLabel(), String.valueOf(type.getId())))
				.collect(Collectors.toList());
	}

	@PostConstruct
	public void initTypes() {
		// init type values
		repo.saveAll(Arrays.asList(
				createType("Go", "go"),
				createType("Java", "java"),
				createType("Kotlin", "kt"),
				createType("Groovy", "groovy"),
				createType("Typescript", "ts"),
				createType("Javascript", "js"),
				createType("HTML", "html"),
				createType("CSS", "css")
		));
	}

	private Type createType(String label, String value) {
		Type t = new Type();
		t.setLabel(label);
		t.setValue(label);
		return t;
	}
}
