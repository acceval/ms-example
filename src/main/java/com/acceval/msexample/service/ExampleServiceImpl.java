package com.acceval.msexample.service;

import static org.apache.commons.collections4.IterableUtils.toList;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.acceval.core.repository.QueryResult;
import com.acceval.msexample.model.Example;
import com.acceval.msexample.model.ExampleDTO;
import com.acceval.msexample.model.Type;
import com.acceval.msexample.repository.ExampleRepository;
import com.acceval.msexample.repository.TypeRepository;
import org.hibernate.cfg.Environment;

/**
 * Example service implementation
 *
 * @author Julian
 */
@Service
public class ExampleServiceImpl implements ExampleService {

	@Autowired
	private ExampleRepository repository;
	@Autowired
	private TypeRepository typeRepository;

	@Override
	public QueryResult<ExampleDTO> query(MultiValueMap<String, String> mapParam) {
		QueryResult<Example> queryResult = repository.queryByMapParam(mapParam);

		return new QueryResult<>(queryResult.getTotal(),
				queryResult.getResults().stream().map(ExampleDTO::mapToDTO).collect(Collectors.toList()));
	}

	@Override
	public ExampleDTO addExample(ExampleDTO dto) {
		Example example = new Example();
		example = mergeDTO(example, dto);
		return ExampleDTO.mapToDTO(repository.save(example));
	}

	@Nonnull
	@Override
	public Optional<ExampleDTO> updateExample(long id, ExampleDTO dto) {
		Optional<Example> example = repository.findById(id);
		if (!example.isPresent()) {
			return Optional.empty();
		}

		Example ex = mergeDTO(example.get(), dto);
		return Optional.of(ExampleDTO.mapToDTO(repository.save(ex)));
	}
	
	@Override
	public void exportSchema() {
		
		Map<String, String> settings = new HashMap<>();
        settings.put(Environment.DRIVER, "org.postgresql.Driver");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
        settings.put(Environment.URL, "jdbc:postgresql://example-postgres:3311/Example?currentSchema=scg");
        settings.put(Environment.USER, "prixlence");
        settings.put(Environment.PASS, "password");
        settings.put(Environment.HBM2DDL_AUTO, "create");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.NON_CONTEXTUAL_LOB_CREATION, "true");
        settings.put(Environment.DEFAULT_SCHEMA, "scg");
        settings.put(Environment.PHYSICAL_NAMING_STRATEGY, "com.acceval.core.config.CustomSpringPhysicalNamingStrategy");
 
        MetadataSources metadata = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySettings(settings)
                        .build());
        metadata.addAnnotatedClass(Example.class);
        metadata.addAnnotatedClass(Type.class);        
         
        SchemaExport schemaExport = new SchemaExport();

        schemaExport.setHaltOnError(true);
        schemaExport.setFormat(true);
//        schemaExport.setDelimiter(";");
//        schemaExport.setOutputFile("db-schema.sql");
        
        schemaExport.createOnly(EnumSet.of(TargetType.DATABASE), metadata.buildMetadata());
                
	}
	
	@Override
	public void updateSchema() {
		
		Map<String, String> settings = new HashMap<>();
        settings.put(Environment.DRIVER, "org.postgresql.Driver");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
        settings.put(Environment.URL, "jdbc:postgresql://example-postgres:3311/Example");
        settings.put(Environment.USER, "prixlence");
        settings.put(Environment.PASS, "password");
        settings.put(Environment.HBM2DDL_AUTO, "update");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.NON_CONTEXTUAL_LOB_CREATION, "true");
        settings.put(Environment.DEFAULT_SCHEMA, "scg");
        settings.put(Environment.PHYSICAL_NAMING_STRATEGY, "com.acceval.core.config.CustomSpringPhysicalNamingStrategy");
        
        MetadataSources metadata = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySettings(settings)
                        .build());
        metadata.addAnnotatedClass(Example.class);
        metadata.addAnnotatedClass(Type.class);        
         
        SchemaUpdate schemaUpdate = new SchemaUpdate();

        schemaUpdate.setHaltOnError(true);
        schemaUpdate.setFormat(true);
        
        schemaUpdate.execute(EnumSet.of(TargetType.DATABASE), metadata.buildMetadata());
	}

	@Nonnull
	@Override
	public Optional<ExampleDTO> getExample(long id) {
		return Optional.of(ExampleDTO.mapToDTO(repository.findById(id).get()));
	}

	@Override
	public void deleteExamples(long... id) {
		Iterable<Example> examples = repository.findAllById(Arrays.stream(id).boxed().collect(Collectors.toList()));

		repository.deleteAll(examples);
	}

	private Example mergeDTO(Example example, ExampleDTO dto) {
		example.setName(dto.getName());
		example.setDate(dto.getDate());

		example.setType(typeRepository.findById(dto.getType()).get());
		example.setRadioType(typeRepository.findById(dto.getRadioType()).get());

		example.setType2(toList(typeRepository.findAllById(dto.getType2())));
		example.setCheckboxType(toList(typeRepository.findAllById(dto.getCheckboxType())));

		example.setCheckbox(dto.isCheckbox());

		example.setAutocomplete(typeRepository.findById(dto.getAutocomplete()).get());

		return example;
	}
}
