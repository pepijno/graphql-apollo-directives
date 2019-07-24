package com.example.demo;

import com.apollographql.federation.graphqljava.Federation;
import com.apollographql.federation.graphqljava.SchemaTransformer;
import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class GraphQLController {

	private final GraphQL graphQL;

	@Autowired
	public GraphQLController(final Query query) {
		final var graphQLSchemaGenerator = new GraphQLSchemaGenerator();

		graphQLSchemaGenerator.withBasePackages("com.example.demo")
							  .withOperationsFromSingleton(query, Query.class);

		final var graphQLSchema = graphQLSchemaGenerator.generate();

		this.graphQL = GraphQL.newGraphQL(Federation.transform(graphQLSchema).build()).build();
	}

	@PostMapping(value = "/api/graphql",
				 consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
				 produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> indexFromAnnotated(@RequestBody final Map<String, Object> request,
												  final HttpServletRequest raw) {
		final var executionInput = this.getExecutionInput(request, raw);
		return this.graphQL.execute(executionInput).toSpecification();
	}

	private ExecutionInput getExecutionInput(@RequestBody Map<String, Object> request, HttpServletRequest raw) {
		final Map<String, Object> variables = (request.get("variables") == null)
											  ? new HashMap<>()
											  : (Map<String, Object>) request.get("variables");

		return ExecutionInput.newExecutionInput()
							 .query((String) request.get("query"))
							 .operationName((String) request.get("operationName"))
							 .variables(variables)
							 .context(raw)
							 .build();
	}
}
