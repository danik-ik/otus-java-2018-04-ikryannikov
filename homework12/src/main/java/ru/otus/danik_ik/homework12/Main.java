package ru.otus.danik_ik.homework12;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.otus.danik_ik.homework11.cache.CacheEngine;
import ru.otus.danik_ik.homework11.cache.CacheHelper;
import ru.otus.danik_ik.homework11.cachedstorage.DbServiceCached;
import ru.otus.danik_ik.homework11.hibernateStorage.DbServiceImpl;
import ru.otus.danik_ik.homework11.storage.DBService;
import ru.otus.danik_ik.homework11.storage.dataSets.UserDataSet;
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

        CacheEngine<Long, UserDataSet> cacheEngine = CacheHelper.getSoftCache(1000);
        DBService dbService = new DbServiceCached(new DbServiceImpl(), cacheEngine);

        context.addServlet(LoginServlet.class, "/login");
        context.addServlet(AdminServlet.class, "/admin");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}
