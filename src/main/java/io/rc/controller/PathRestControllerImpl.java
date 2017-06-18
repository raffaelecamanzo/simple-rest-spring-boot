package io.rc.controller;

import io.rc.service.MyResourceService;
import io.rc.simple.spring.boot.rest.api.PathRestController;
import io.rc.simple.spring.boot.rest.model.Error;
import io.rc.simple.spring.boot.rest.model.MyResource;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * User:    raffaelecamanzo
 * Date:    17/06/2017
 * <p>
 * Description:
 *   /path RESTful API implementation
 */
@RestController
public class PathRestControllerImpl implements PathRestController {

	private MyResourceService myResourceService;
	@Autowired
	public void setMyResourceService(MyResourceService myResourceService) {
		this.myResourceService = myResourceService;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}

	@Override
	@RequestMapping(value="/path/to/resources/{resource_id}",method=RequestMethod.DELETE,consumes={ "application/json" } , produces={ "application/json" })
	@ApiOperation(value = "Delete a resource", tags = { "PathsAndMethodsAvailable" }, notes = "Delete the resource with the given id ", response = MyResource.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource deleted (get it if you need it!)"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Unexpected error")
	})
	public ResponseEntity deleteResource(
			// "resourceId"
			@ApiParam(value = "The resource id.", required=true )
			@PathVariable("resource_id")
					String resourceId) {

		if(myResourceService.isResourceExists(resourceId))
			return new ResponseEntity(myResourceService.deleteResource(resourceId),
					getHeaders(), HttpStatus.OK);

		Error error = new Error.ErrorBuilder()
				.code(HttpStatus.BAD_REQUEST.value())
				.message("No resource with the given id")
				.fields(resourceId)
				.build();

		return new ResponseEntity(error, getHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	@RequestMapping(value="/path/to/resources",method=RequestMethod.GET , produces={ "application/json" })
	@ApiOperation(value = "Get the resources", tags = { "PathsAndMethodsAvailable" }, notes = "A way to retrieve the resources ", response = MyResource.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Everything ok!"),
			@ApiResponse(code = 500, message = "Unexpected error")
	})
	public ResponseEntity getAllTheResources(
			// "meaningfulQueryParam"
			@ApiParam(value = "something which makes sense in your domain")
			@RequestParam(value="meaningful_query_param", required=false)
					String meaningfulQueryParam) {

		return new ResponseEntity(myResourceService.getResourceList(),
				getHeaders(), HttpStatus.OK);
	}

	@Override
	@RequestMapping(value="/path/to/resources/{resource_id}",method=RequestMethod.GET , produces={ "application/json" })
	@ApiOperation(value = "Get a specific resource", tags = { "PathsAndMethodsAvailable" }, notes = "The resource i want. ", response = MyResource.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Everything ok!"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Unexpected error")
	})
	public ResponseEntity getTheResourcesWithId(
			// "resourceId"
			@ApiParam(value = "The resource id.", required=true )
			@PathVariable("resource_id")
					String resourceId) {

		if(myResourceService.isResourceExists(resourceId))
			return new ResponseEntity(myResourceService.getResource(resourceId),
					getHeaders(), HttpStatus.OK);

		Error error = new Error.ErrorBuilder()
				.code(HttpStatus.BAD_REQUEST.value())
				.message("No resource with the given id")
				.fields(resourceId)
				.build();

		return new ResponseEntity(error, getHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	@RequestMapping(value="/path/to/resources",method=RequestMethod.HEAD,consumes={ "application/json" } , produces={ "application/json" })
	@ApiOperation(value = "Get the resources header", tags = { "PathsAndMethodsAvailable" }, notes = "My resources header ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Everything ok!"),
			@ApiResponse(code = 500, message = "Unexpected error")
	})
	public ResponseEntity headForAllTheResources(
			// "meaningfulQueryParam"
			@ApiParam(value = "something which makes sense in your domain")
			@RequestParam(value="meaningful_query_param", required=false)
					String meaningfulQueryParam) {

		return new ResponseEntity(null, getHeaders(), HttpStatus.OK);
	}

	@Override
	@RequestMapping(value="/path/to/resources",method=RequestMethod.POST,consumes={ "application/json" } , produces={ "application/json" })
	@ApiOperation(value = "Add a new resource", tags = { "PathsAndMethodsAvailable" }, notes = "Add a new resource to the list of resources ")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Resource inserted!"),
			@ApiResponse(code = 400, message = "Wrong payload"),
			@ApiResponse(code = 500, message = "Unexpected error")
	})
	public ResponseEntity insertNewResource(
			// "payload"
			@ApiParam(value = "The resource to add", required=true)
			@RequestBody
					MyResource payload) {

		if(myResourceService.isResourceExists(payload.getResourceId()))
			return new ResponseEntity(new Error.ErrorBuilder()
						.code(HttpStatus.BAD_REQUEST.value())
						.message("Resource already inserted with the given resource ID")
						.fields(payload.getResourceId())
						.build(),
					getHeaders(), HttpStatus.BAD_REQUEST);

		myResourceService.addResource(payload.getResourceId(), payload);

		return new ResponseEntity(null, getHeaders(), HttpStatus.CREATED);
	}

	@Override
	@RequestMapping(value="/path/to/resources/{resource_id}",method=RequestMethod.PUT,consumes={ "application/json" } , produces={ "application/json" })
	@ApiOperation(value = "Modify a resource", tags = { "PathsAndMethodsAvailable" }, notes = "Modify the resource with the given id ")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Resource modified!"),
			@ApiResponse(code = 400, message = "Wrong payload"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Unexpected error")
	})
	public ResponseEntity updateResource(
			// "resourceId"
			@ApiParam(value = "The resource id.", required=true )
			@PathVariable("resource_id")
					String resourceId,
			// "payload"
			@ApiParam(value = "The resource to add", required=true)
			@RequestBody
					MyResource payload) {

		if(!myResourceService.isResourceExists(resourceId))
			return new ResponseEntity(new Error.ErrorBuilder()
						.code(HttpStatus.BAD_REQUEST.value())
						.message("No resource to update with the given ID")
						.fields(resourceId)
						.build(),
					getHeaders(), HttpStatus.BAD_REQUEST);

		myResourceService.modifyResource(resourceId, payload);
		return new ResponseEntity(null, getHeaders(), HttpStatus.NO_CONTENT);
	}
}
