package com.github.adalrsjr1.ms_calculator.frontend.v2

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

class CalculatorV2 {
	private static final String DESTINATION = "http://localhost:8880/calculator/v2/"

	private static final Logger logger = LoggerFactory.getLogger(CalculatorV2.class);


	Response operation(HttpHeaders headers, String op, double a, double b) {
		String uuid
		int loops
		if(headers.requestHeaders.containsKey("X-UUID")) {
			uuid = headers.requestHeaders["X-UUID"][0]
		}
		else {
			uuid = UUID.randomUUID()
		}

		logger.info ">>> [${op}] uuid: $uuid"

		if(headers.requestHeaders.containsKey("X-Loops")) {
			loops = Integer.parseInt(headers.requestHeaders["X-Loops"][0])
		}
		else {
			loops = 0
		}

		URL url = b ? new URL(DESTINATION + "${op}/${a}/${b}") : new URL(DESTINATION + "${op}/${a}")
		HttpURLConnection connection = (HttpURLConnection) url.openConnection()
		connection.setDoOutput(true)
		connection.setInstanceFollowRedirects(true)
		connection.setRequestMethod("GET")
		connection.setRequestProperty("Content-Type", "text/plain")
		connection.setRequestProperty("X-UUID", uuid)
		connection.setRequestProperty("X-Loops", "$loops")

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.inputStream, "UTF-8"))
		StringBuffer sb = new StringBuffer()
		for (String line; (line = reader.readLine()) != null;) {
			sb.append(line)
		}

		loops = Integer.parseInt(connection.getHeaderField("X-Loops")) + 1


		int responseCode = connection.getResponseCode()
		connection.disconnect()
		reader.close()

		Response.status(responseCode)
				.entity(sb.toString())
				.header("X-UUID", uuid)
				.header("X-Loops", loops)
				.build()
	}

	@GET
	@Path("sum/{a}/{b}")
	@Produces(MediaType.TEXT_PLAIN)
	Response sum(@Context HttpHeaders headers,
			@PathParam("a") double a,
			@PathParam("b") double b) {

		operation(headers, "sum", a, b);
	}

	@GET
	@Path("sub/{a}/{b}")
	@Produces(MediaType.TEXT_PLAIN)
	Response sub(@Context HttpHeaders headers,
			@PathParam("a") double a,
			@PathParam("b") double b) {

		operation(headers, "sub", a, b);
	}

	@GET
	@Path("mult/{a}/{b}")
	@Produces(MediaType.TEXT_PLAIN)
	Response mult(@Context HttpHeaders headers,
			@PathParam("a") double a,
			@PathParam("b") double b) {

		operation(headers, "mult", a, b);
	}

	@GET
	@Path("div/{a}/{b}")
	@Produces(MediaType.TEXT_PLAIN)
	Response div(@Context HttpHeaders headers,
			@PathParam("a") double a,
			@PathParam("b") double b) {

		operation(headers, "mult", a, b);
	}

	@GET
	@Path("pow/{a}/{b}")
	@Produces(MediaType.TEXT_PLAIN)
	Response pow(@Context HttpHeaders headers,
			@PathParam("a") double a,
			@PathParam("b") double b) {

		operation(headers, "mult", a, b);
	}

	@GET
	@Path("sqrt/{a}")
	@Produces(MediaType.TEXT_PLAIN)
	Response sqrt(@Context HttpHeaders headers,
			@PathParam("a") double a) {

		operation(headers, "sqrt", a, null);
	}
}
