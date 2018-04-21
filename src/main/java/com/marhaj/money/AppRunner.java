package com.marhaj.money;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import com.marhaj.money.account.AccountEndpoint;
import com.marhaj.money.transfer.TransferEndpoint;
import com.marhaj.money.user.UserEndpoint;

public class AppRunner {

	public static void main(String[] args) throws Exception {

		Server server = new Server(8080);
		ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.SESSIONS);
		ctx.setContextPath("/");
		server.setHandler(ctx);
		ServletHolder servletHolder = ctx.addServlet(ServletContainer.class, "/*");
		servletHolder.setInitParameter("jersey.config.server.provider.classnames",
				UserEndpoint.class.getCanonicalName() + "," + AccountEndpoint.class.getCanonicalName() + ","
						+ TransferEndpoint.class.getCanonicalName());
		server.start();
		server.join();
	}
}
