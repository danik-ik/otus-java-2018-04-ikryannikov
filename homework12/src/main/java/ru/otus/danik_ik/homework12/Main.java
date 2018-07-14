package ru.otus.danik_ik.homework12;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.otus.danik_ik.homework12.servlet.AdminServlet;
import ru.otus.danik_ik.homework12.servlet.LoginServlet;

public class Main
{
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "public_html";

    public static void main( String[] args ) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(LoginServlet.class, "/login");
        context.addServlet(AdminServlet.class, "/admin");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}
