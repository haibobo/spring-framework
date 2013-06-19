/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.messaging.support;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import reactor.util.Assert;


/**
 * @author Rossen Stoyanchev
 * @since 4.0
 */
@SuppressWarnings("rawtypes")
public class SessionMessageChannel<M extends Message> implements MessageChannel<M> {

	private MessageChannel<M> delegate;

	private final String sessionId;


	public SessionMessageChannel(MessageChannel<M> delegate, String sessionId) {
		Assert.notNull(delegate, "delegate is required");
		Assert.notNull(sessionId, "sessionId is required");
		this.sessionId = sessionId;
		this.delegate = delegate;
	}

	@Override
	public boolean send(M message) {
		return send(message, -1);
	}

	@Override
	public boolean send(M message, long timeout) {
		PubSubHeaderAccesssor headers = PubSubHeaderAccesssor.wrap(message);
		headers.setSessionId(this.sessionId);
		Object payload = message.getPayload();
		@SuppressWarnings("unchecked")
		M messageToSend = (M) MessageBuilder.withPayload(payload).copyHeaders(headers.toHeaders()).build();
		this.delegate.send(messageToSend);
		return true;
	}
}
