/**
 * Copyright (C) 2012 FuseSource Corp. All rights reserved.
 * http://fusesource.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fusesource.mqhooks;

import com.sun.jersey.api.core.ResourceContext;

import javax.jms.Destination;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

abstract public class DestinationResource {

    @Context
    ResourceContext rc;

    @POST
    @Path("/{destination}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public String createConsumer(@PathParam("destination")String destinationName, Consumer consumer) throws Exception {
        Destination destination = createDestination(destinationName);
        getService().addConsumer(destination, consumer);
        return consumer.getId();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{destination}")
    public HashMap<String, Consumer> getConsumers(@PathParam("destination")String destination) {
        return fetchConsumers(createDestination(destination));
    }

    @DELETE
    @Path("/{destination}/{id}")
    public void getConsumer(@PathParam("destination")String destination, @PathParam("id")String id) {
        getService().deleteConsumer(createDestination(destination), id);
    }

    protected Service getService() {
        return rc.getResource(Service.class);
    }

    public HashMap<String, Consumer> fetchConsumers(Destination destination) {
        return getService().getConsumers(destination);
    }

    abstract protected Destination createDestination(String destinationName);

}
