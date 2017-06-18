package io.rc.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.rc.simple.spring.boot.rest.model.MyResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User:    raffaelecamanzo
 * Date:    17/06/2017
 * <p>
 * Description:
 *   Service class for MyResource handling
 */
@Service
public class MyResourceService {

	private static final Logger LOG = LoggerFactory.getLogger(MyResourceService.class);

	private Map<String, MyResource> myResourceMap = Maps.newHashMap();

	public boolean isResourceExists(String resourceId) {
		return myResourceMap.containsKey(resourceId);
	}

	public void addResource(String resourceId, MyResource myResource) {
		LOG.info("Adding resource: {}", myResource);
		myResourceMap.put(resourceId, myResource);
	}

	public void modifyResource(String resourceId, MyResource myResource) {
		if(myResourceMap.containsKey(resourceId))
			myResourceMap.put(resourceId, myResource);
	}

	public MyResource deleteResource(String resourceId) {
		if(myResourceMap.containsKey(resourceId))
			return myResourceMap.remove(resourceId);

		return null;
	}

	public MyResource getResource(String resourceId) {
		return myResourceMap.get(resourceId);
	}

	public List<MyResource> getResourceList() {
		return Lists.newArrayList(myResourceMap.values());
	}
}
