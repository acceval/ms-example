package com.acceval.msexample.type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private TypeRepository repo;

	@RequestMapping(method = RequestMethod.GET, value = "/type")
	public List<LabelValue> getTypeDataSource(@RequestParam(value = "q", defaultValue = "") String query) {
		return StreamSupport.stream(repo.findAll().spliterator(), false)
				.filter(type -> StringUtils.isBlank(query) || type.getLabel().toLowerCase().contains(query
						.toLowerCase()))
				.map(type -> new LabelValue(type.getLabel(), String.valueOf(type.getId())))
				.collect(Collectors.toList());
	}

	@PostConstruct
	public void initTypes() {
		 
		// init type values
		repo.saveAll(Arrays.asList(
				createType(100000000, "Go", "go"),
				createType(100000001, "Java", "java"),
				createType(100000002, "Kotlin", "kt"),
				createType(100000003, "Groovy", "groovy"),
				createType(100000004, "Typescript", "ts"),
				createType(100000005, "Javascript", "js"),
				createType(100000006, "HTML", "html"),
				createType(100000007, "CSS", "css")
		));
	}

	private Type createType(String label, String value) {
		return createType(0, label, value);
	}

	private Type createType(long id, String label, String value) {
		Type t = new Type();
		t.setId(id);
		t.setLabel(label);
		t.setValue(value);
		return t;
	}
}
