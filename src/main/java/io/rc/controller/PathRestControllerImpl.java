package io.rc.controller;

import io.rc.service.MyResourceService;
import io.rc.simple.spring.boot.rest.api.PathRestController;
import io.rc.simple.spring.boot.rest.model.Error;
import io.rc.simple.spring.boot.rest.model.MyResource;
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

	@RequestMapping(value="/path/to/resources/{resource_id}",method=RequestMethod.DELETE,consumes={ "application/json" } , produces={ "application/json" })
	public ResponseEntity deleteResource(
			// "resourceId"
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

	@RequestMapping(value="/path/to/resources",method=RequestMethod.GET , produces={ "application/json" })
	public ResponseEntity getAllTheResources(
			// "meaningfulQueryParam"
			@RequestParam(value="meaningful_query_param", required=false)
					String meaningfulQueryParam) {

		return new ResponseEntity(myResourceService.getResourceList(),
				getHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value="/path/to/resources/{resource_id}",method=RequestMethod.GET , produces={ "application/json" })
	public ResponseEntity getTheResourcesWithId(
			// "resourceId"
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

	@RequestMapping(value="/path/to/resources",method=RequestMethod.HEAD,consumes={ "application/json" } , produces={ "application/json" })
	public ResponseEntity headForAllTheResources(
			// "meaningfulQueryParam"
			@RequestParam(value="meaningful_query_param", required=false)
					String meaningfulQueryParam) {

		return new ResponseEntity(null, getHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value="/path/to/resources",method=RequestMethod.POST,consumes={ "application/json" } , produces={ "application/json" })
	public ResponseEntity insertNewResource(
			// "payload"
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

	@RequestMapping(value="/path/to/resources/{resource_id}",method=RequestMethod.PUT,consumes={ "application/json" } , produces={ "application/json" })
	public ResponseEntity updateResource(
			// "resourceId"
			@PathVariable("resource_id")
					String resourceId,
			// "payload"
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
