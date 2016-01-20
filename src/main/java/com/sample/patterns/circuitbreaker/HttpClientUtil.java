package com.sample.patterns.circuitbreaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

/**
 * The Class HttpClientUtil.
 * 
 * @author saandey
 * 
 * 
 */
public class HttpClientUtil {

	/**
	 * Private constructor to prevent class instantiation.
	 */
	private HttpClientUtil() {
	}

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = Logger.getLogger(HttpClientUtil.class);

	/**
	 * Gets the http data.
	 * 
	 * @param url
	 *            the url
	 * @return the http data
	 */
	public static String getHttpData(String url) throws IOException {
		String httpResponse = null;
		LOGGER.debug("HttpClientUtil :: getHttpData :: Entered");
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet httpGet = new HttpGet(url);

			RequestConfig requestConfig = setConnectionTimeouts();
			httpGet.setConfig(requestConfig);
			HttpResponse response = client.execute(httpGet);

			LOGGER.debug("HttpClientUtil :: getHttpData :: Response Code : "
					+ response.getStatusLine().getStatusCode());

			HttpEntity httpEntity = response.getEntity();
			InputStream stream = httpEntity.getContent();
			httpResponse = getInputStream(stream);
			LOGGER.debug("HttpClientUtil :: getHttpData :: Response "
					+ httpResponse);
		} catch (ClientProtocolException e) {
			LOGGER.error(
					"HttpClientUtil :: getHttpData :: ClientProtocolException  ",
					e);
			throw e;
		} catch (IOException e) {
			LOGGER.error("HttpClientUtil :: getHttpData :: IOException  ", e);
			throw e;
		}

		return httpResponse;

	}

	/**
	 * Gets the input stream.
	 * 
	 * @param stream
	 *            the stream
	 * @return the input stream
	 */
	public static String getInputStream(InputStream stream) {
		BufferedReader rd = new BufferedReader(new InputStreamReader(stream));

		StringBuffer result = new StringBuffer();
		String line = "";
		try {
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			LOGGER.error("IOException in streamReader method.", e);
		} finally {

			try {
				stream.close();

			} catch (IOException e) {
				LOGGER.error("IOException in streamReader method.", e);
			}
		}
		return result.toString();
	}

	/**
	 * Sets the connection timeouts.
	 * 
	 * @return the request config
	 */
	public static RequestConfig setConnectionTimeouts() {

		RequestConfig defaultRequestConfig = getDeafultRequestConfig();
		// Request configuration can be overridden at the request level.
		// They will take precedence over the one set at the client level.

		// int socketTimout = Integer.parseInt(ReportsPropertyUtil
		// .getProperty(ReportConstants.HTTP_SOCKET_TIMEOUT));
		// int connectionTimeout = Integer.parseInt(ReportsPropertyUtil
		// .getProperty(ReportConstants.HTTP_CONN_TIMEOUT));
		// int connectionRequestTimeout = Integer.parseInt(ReportsPropertyUtil
		// .getProperty(ReportConstants.HTTP_CONN_REQ_TIMEOUT));
		RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
				.setSocketTimeout(9000).setConnectTimeout(9000)
				.setConnectionRequestTimeout(9000).build();
		return requestConfig;
	}

	/**
	 * Gets the deafult request config.
	 * 
	 * @return the deafult request config
	 */
	public static RequestConfig getDeafultRequestConfig() {
		// Create global request configuration
		RequestConfig defaultRequestConfig = RequestConfig
				.custom()
				.setCookieSpec(CookieSpecs.STANDARD)
				.setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(
						Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
				.build();
		return defaultRequestConfig;

	}
}
