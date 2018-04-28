package com.marhaj.money;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.marhaj.money.account.Account;
import com.marhaj.money.account.AccountEndpoint;
import com.marhaj.money.transfer.TransferEndpoint;
import com.marhaj.money.transfer.dto.Transfer;
import com.marhaj.money.user.UserEndpoint;
import com.marhaj.money.user.dto.User;

public abstract class BaseEndpoint {
	protected static Server server;
	protected static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
	protected static ObjectMapper mapper = new ObjectMapper();
	protected static URIBuilder builder = new URIBuilder().setScheme("http").setHost("localhost:8088");
	protected static HttpClient client;
	protected static String userNameA = "userA";
	protected static String userNameB = "userB";
	protected static String userEmail = "user@user.com";
	protected static String userPhone = "12345678";
	protected static BigDecimal balanceA = new BigDecimal(100);
	protected static BigDecimal balanceB = new BigDecimal(200);

	@BeforeClass
	public static void setup() throws Exception {
		if (null == server) {
			server = new Server(8088);
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setContextPath("/");
			server.setHandler(context);
			ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
			servletHolder.setInitParameter("jersey.config.server.provider.classnames",
					UserEndpoint.class.getCanonicalName() + "," + AccountEndpoint.class.getCanonicalName() + ","
							+ TransferEndpoint.class.getCanonicalName());
			server.start();
		}
		connManager.setDefaultMaxPerRoute(100);
		connManager.setMaxTotal(200);
		client = HttpClients.custom().setConnectionManager(connManager).setConnectionManagerShared(true).build();
	}

	@AfterClass
	public static void close() throws Exception {
		HttpClientUtils.closeQuietly(client);
		// server.stop();
		// server.destroy();
	}

	protected static void deleteEntity(String path) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = builder.setPath(path).build();
		HttpDelete request = new HttpDelete(uri);
		request.setHeader("Content-type", "application/json");
		client.execute(request);
	}

	protected static String getEntity(String path) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = builder.setPath(path).build();
		HttpGet request = new HttpGet(uri);
		request.setHeader("Content-type", "application/json");
		HttpResponse response = client.execute(request);
		return EntityUtils.toString(response.getEntity());
	}

	protected static <T> List<T> getAll(Class clazz)
			throws URISyntaxException, IOException, ClientProtocolException, JsonParseException, JsonMappingException {
		String path = null;
		if (Transfer.class.equals(clazz)) {
			path = "/transfer/all";
		} else if (Account.class.equals(clazz)) {
			path = "/account/all";
		} else if (User.class.equals(clazz)) {
			path = "/user/all";
		} else {
			Assert.fail(" Unknown Entity type : " + clazz.getName());
		}

		URI uri = builder.setPath(path).build();
		HttpGet request = new HttpGet(uri);
		request.setHeader("Content-type", "application/json");
		HttpResponse response = client.execute(request);
		String receiveJson = EntityUtils.toString(response.getEntity());

		CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
		return mapper.readValue(receiveJson, javaType);
	}

	public static <T> T saveEntity(T t) throws URISyntaxException, ClientProtocolException, IOException {
		String path = null;
		if (t instanceof Transfer) {
			path = "/transfer/save";
		} else if (t instanceof Account) {
			path = "account/save";
		} else if (t instanceof User) {
			path = "user/save";
		} else {
			Assert.fail(" Unknown Entity type : " + t.getClass().getName());
		}

		URI uri = builder.setPath(path).build();
		String json = mapper.writeValueAsString(t);
		StringEntity entity = new StringEntity(json);
		HttpPost request = new HttpPost(uri);
		request.setHeader("Content-type", "application/json");
		request.setEntity(entity);
		HttpResponse response = client.execute(request);
		String reciveJson = EntityUtils.toString(response.getEntity());
		return (T) mapper.readValue(reciveJson, t.getClass());
	}
}
