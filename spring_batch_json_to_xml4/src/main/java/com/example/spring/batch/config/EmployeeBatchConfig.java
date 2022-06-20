package com.example.spring.batch.config;

import java.io.IOException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.example.spring.batch.model1.Employee;
import com.example.spring.batch.processor.EmployeeProcessor;

@Configuration
public class EmployeeBatchConfig {

	@Autowired
	private JobBuilderFactory job;
	
	@Autowired
	private StepBuilderFactory step;
	
	@Autowired
	private EmployeeProcessor process;
	@Bean
    public JsonItemReader<Employee> reader() {
        return new JsonItemReaderBuilder<Employee>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Employee.class))
                .resource(new FileSystemResource("InputFiles/trades.json"))
                .name("studentJsonItemReader")
                .build();
    }
    
    
    
    
    @Bean
    public StaxEventItemWriter<Employee> writer() {
    	StaxEventItemWriter<Employee> writer = new StaxEventItemWriter<>();
    			writer.setResource(new FileSystemResource("OutputFiles/trades.xml"));
    			writer.setRootTagName("employees");
    			writer.setMarshaller(new Jaxb2Marshaller() {
    				{
    					setClassesToBeBound(Employee.class);
    				}
    			});
    			return writer;
    }
    
    @Bean
	public Step step1() throws IOException {
		return step.get("step").<Employee, Employee>chunk(3).reader(reader()).processor(process).writer(writer())
				.build();
	}

	@Bean("firstJob")
	public Job firstJob() throws IOException {
		return job.get("first Job").incrementer(new RunIdIncrementer()).start(step1()).build();
	}
    
    
     
}
