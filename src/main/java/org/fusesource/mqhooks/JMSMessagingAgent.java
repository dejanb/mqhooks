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

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

public class JMSMessagingAgent {

    ConnectionFactory factory;
    Connection connection;
    Session session;

    public JMSMessagingAgent(ConnectionFactory factory) {
        this.factory = factory;
    }

    public Connection getConnection() throws JMSException {
        if (connection == null) {
            connection = factory.createConnection();
            connection.start();
        }

        return connection;
    }

    public Session getSession() throws JMSException {
        if (session == null) {
            session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
        }

        return session;
    }

    public MessageConsumer createConsumer(Destination destination) throws JMSException {
        return getSession().createConsumer(destination);
    }



}
