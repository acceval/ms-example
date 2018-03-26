package com.acceval.msexample.type;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acceval.core.microservice.model.LabelValue;

/**
 * TODO: Document this
 *
 * @author Julian
 */
@RestController
@RequestMapping("/datasource")
public class DataSourceController {
	@RequestMapping(method = RequestMethod.GET, value = "/type")
	public List<LabelValue> getTypeDataSource() {
		return Arrays.asList(
				new LabelValue("Go", "go"),
				new LabelValue("Java", "java"),
				new LabelValue("Kotlin", "kt"),
				new LabelValue("Groovy", "groovy"),
				new LabelValue("Typescript", "ts"),
				new LabelValue("Javascript", "js"),
				new LabelValue("HTML", "html"),
				new LabelValue("CSS", "css")
		);
	}
}
