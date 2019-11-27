package com.cs509.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import org.junit.Test;

public class DeleteImageElementHandlerTest {
	@Test
	public void deleteImageTest() {
		DeleteImageElementHandler handler = new DeleteImageElementHandler();
		String initialString = "{}";
		InputStream testInputStream = new ByteArrayInputStream(initialString.getBytes());
        OutputStream testOutputStream = new ByteArrayOutputStream();
        Context context = new Context() {

			@Override
			public String getAwsRequestId() {
				return null;
			}

			@Override
			public String getLogGroupName() {
				return null;
			}

			@Override
			public String getLogStreamName() {
				return null;
			}

			@Override
			public String getFunctionName() {
				return null;
			}

			@Override
			public String getFunctionVersion() {
				return null;
			}

			@Override
			public String getInvokedFunctionArn() {
				return null;
			}

			@Override
			public CognitoIdentity getIdentity() {
				return null;
			}

			@Override
			public ClientContext getClientContext() {
				return null;
			}

			@Override
			public int getRemainingTimeInMillis() {
				return 0;
			}

			@Override
			public int getMemoryLimitInMB() {
				return 0;
			}

			@Override
			public LambdaLogger getLogger() {
				return new LambdaLogger() {

					@Override
					public void log(String string) {
						System.out.println(string);
						
					}
					
				};
			}
			
		};
		try {
			handler.handleRequest(testInputStream, testOutputStream, context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
