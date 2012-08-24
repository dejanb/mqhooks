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

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Future;

@JsonAutoDetect({JsonMethod.NONE})
@Produces({MediaType.APPLICATION_JSON})
@Consumes(MediaType.APPLICATION_JSON)
public class Consumer {

    @JsonProperty
    Integer id;
    @JsonProperty
    URL url;

    MessageConsumer consumer;

    Service service;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public MessageConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(MessageConsumer consumer) throws JMSException {
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    if (message instanceof TextMessage) {
                        System.out.println(((TextMessage)message).getText().trim() + " -> " + url.toString());
                        AsyncHttpClient.BoundRequestBuilder request = service.getHttpClient().preparePost(url.toString());
                        request.setBody(((TextMessage) message).getText());
                        Future<Response> result = request.execute();
                        Response response = result.get();
                        System.out.println(response.getStatusCode() + " " + response.getStatusText() + " " + response.getResponseBody());
                    }
                    //TODO handle other message types
                } catch (Exception e) {
                    e.printStackTrace();
                    //TODO should we use transactions instead?
                    throw new RuntimeException(e);
                }
            }
        });
        this.consumer = consumer;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
