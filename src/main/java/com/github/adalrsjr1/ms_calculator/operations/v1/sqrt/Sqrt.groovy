package com.github.adalrsjr1.ms_calculator.operations.v1.sqrt

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.github.adalrsjr1.ms_calculator.operations.v1.pow.Pow;

@Path("operation/")
class Sqrt {

	private static final Logger log = LoggerFactory.getLogger(Pow.class);
	
	private static final String DESTINATION = "http://localhost:8888/operation/v1"
	
	@GET
	@Path("sqrt/{a}")
	@Produces(MediaType.TEXT_PLAIN)
	Response sqrt(@Context HttpHeaders headers,
			   @PathParam("a") double a) {
		
		String uuid
		int loops
		if(headers.requestHeaders.containsKey("X-UUID")) {
			uuid = headers.requestHeaders["X-UUID"][0]
		}
		else {
			uuid = UUID.randomUUID()
		}
		
		log.info ">>> [sqrt] uuid: $uuid"
		
		if(headers.requestHeaders.containsKey("X-Loops")) {
			loops = Integer.parseInt(headers.requestHeaders["X-Loops"][0])
		}
		else {
			loops = 0
		}

		boolean flag = (new Random(31).nextInt() % 31) % 7 == 0
		
		if(flag) {
			Response.status(200)
					.entity(Math.sqrt(a))
					.header("X-UUID", uuid)
					.header("X-Loops", loops+1)
					.build()
		}
		else {
			Response.status(500)
			.header("X-UUID", uuid)
			.header("X-Loops", loops+1)
			.build()
		}
	}
	
}

