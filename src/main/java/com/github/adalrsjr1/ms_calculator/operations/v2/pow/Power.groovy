package com.github.adalrsjr1.ms_calculator.operations.v2.pow

import java.util.UUID

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

@Path("operation/")
class Pow {

	private static final Logger log = LoggerFactory.getLogger(Pow.class);
	
	@GET
	@Path("pow/{a}/{b}")
	@Produces(MediaType.TEXT_PLAIN)
	Response pow(@Context HttpHeaders headers,
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
		
		log.info ">>> [pow] uuid: $uuid"
		
		if(headers.requestHeaders.containsKey("X-Loops")) {
			loops = Integer.parseInt(headers.requestHeaders["X-Loops"][0])
		}
		else {
			loops = 0
		}

		Response.status(200)
				.entity(Math.pow(a,b))
				.header("X-UUID", uuid)
				.header("X-Loops", loops+1)
				.build()
	}
	
}
