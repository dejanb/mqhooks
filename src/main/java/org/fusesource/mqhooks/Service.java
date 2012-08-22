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
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.Destination;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Singleton
public class Service {

    Map<Destination, HashMap<String, Consumer>> consumers = new HashMap<Destination, HashMap<String, Consumer>>();
    Random rnd = new Random(); //temp key generator

    public HashMap<String, Consumer> getConsumers(Destination destination) {
        HashMap<String, Consumer> result = consumers.get(destination);
        if (result == null) {
            result = new HashMap<String, Consumer>();
            consumers.put(destination, result);
        }
        return result;
    }

    public String addConsumer(Destination destination, Consumer consumer) {
        consumer.setId(createConsumer());
        HashMap<String, Consumer> consumerList = getConsumers(destination);
        consumerList.put(consumer.getId(), consumer);
        return consumer.getId();
    }

    public void deleteConsumer(Destination destination, String id) {
        //TODO unsubscribe an actual consumer
        getConsumers(destination).remove(id);
    }

    public String createConsumer() {
        //TODO create an actual consumer
        return String.valueOf(rnd.nextInt(100000));
    }


}
