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

import com.sun.jersey.spi.resource.Singleton;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class Service {

    Map<Destination, HashMap<Integer, Consumer>> consumers = new HashMap<Destination, HashMap<Integer, Consumer>>();
    AtomicInteger id = new AtomicInteger(1);
    JMSMessagingAgent messagingAgent;

    public Service() {
        //TODO config mechanism for connection factory
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        messagingAgent = new JMSMessagingAgent(factory);
    }

    public HashMap<Integer, Consumer> getConsumers(Destination destination) {
        HashMap<Integer, Consumer> result = consumers.get(destination);
        if (result == null) {
            result = new HashMap<Integer, Consumer>();
            consumers.put(destination, result);
        }
        return result;
    }

    public Integer addConsumer(Destination destination, Consumer consumer) throws JMSException {
        consumer.setConsumer(messagingAgent.createConsumer(destination));
        consumer.setId(id.getAndIncrement());
        HashMap<Integer, Consumer> consumerList = getConsumers(destination);
        consumerList.put(consumer.getId(), consumer);
        return consumer.getId();
    }

    public void deleteConsumer(Destination destination, String id) {
        //TODO unsubscribe an actual consumer
        getConsumers(destination).remove(id);
    }

}
