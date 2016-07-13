package com.github.adalrsjr1.ms_calculator.operations.v2;

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Path("operation/v1")
public class Sum {

	private static final Logger log = LoggerFactory.getLogger(Sum.class);
	
	private static final String DESTINATION = "http://localhost:8888/calculator/v1"
	
	@GET
	@Path("sum/{a}/{b}")
	@Produces(MediaType.TEXT_PLAIN)
	Response sum(@Context HttpHeaders headers,
			   @PathParam("a") double a, 
			   @PathParam("b") double b) {
		
		String uuid
		int loops
		if(headers.requestHeaders.containsKey("X-UUID")) {
			uuid = headers.requestHeaders["X-UUID"][0]
		}
		else {
			uuid = UUID.randomUUID()
		}
		
		log.info ">>> [sum] uuid: $uuid"
		
		if(headers.requestHeaders.containsKey("X-Loops")) {
			loops = Integer.parseInt(headers.requestHeaders["X-Loops"][0])
		}
		else {
			loops = 0
		}

		Response.status(200)
		        .entity(a+b)
				.header("X-UUID", uuid)
				.header("X-Loops", loops+1)
				.build()
	}
	
}
