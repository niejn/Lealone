/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codefollower.lealone.atomicdb.transport.messages;

import org.jboss.netty.buffer.ChannelBuffer;

import com.codefollower.lealone.atomicdb.transport.CBUtil;
import com.codefollower.lealone.atomicdb.transport.Message;

/**
 * Message to indicate that the server is ready to receive requests.
 */
public class AuthenticateMessage extends Message.Response
{
    public static final Message.Codec<AuthenticateMessage> codec = new Message.Codec<AuthenticateMessage>()
    {
        public AuthenticateMessage decode(ChannelBuffer body, int version)
        {
            String authenticator = CBUtil.readString(body);
            return new AuthenticateMessage(authenticator);
        }

        public void encode(AuthenticateMessage msg, ChannelBuffer dest, int version)
        {
            CBUtil.writeString(msg.authenticator, dest);
        }

        public int encodedSize(AuthenticateMessage msg, int version)
        {
            return CBUtil.sizeOfString(msg.authenticator);
        }
    };

    public final String authenticator;

    public AuthenticateMessage(String authenticator)
    {
        super(Message.Type.AUTHENTICATE);
        this.authenticator = authenticator;
    }

    @Override
    public String toString()
    {
        return "AUTHENTICATE " + authenticator;
    }
}