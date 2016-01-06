package main;

import base.Frontend;
import base.AccountService;
import base.GameMechanics;
import base.WebSocketService;
import frontend.game.WebSocketGameServlet;
import frontend.game.WebSocketServiceImpl;
import frontend.workingWithUsers.*;
import mechanics.GameMechanicsImpl;
import services.AccountService.AccountServiceImpl;
import admin.AdminServlet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.UserProfile.UserProfile;

import javax.servlet.Servlet;

public class Main {

    public static void main(String[] args) throws  Exception, NumberFormatException, InterruptedException {
        if (args.length != 1) {
            System.out.append("Use port as the first argument");
            System.exit(1);
        }

        String portString = args[0];
        int port = Integer.valueOf(portString);
        System.out.append("Starting at port: ").append(portString).append('\n');

        //AccountService accountService = new AccountServiceImpl();
        AccountService accountService = new AccountServiceImpl();
        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);

        for(int i = 1; i < 11; ++i) {
            accountService.singUp(new UserProfile("user" + String.valueOf(i), "123456", "qwe@mail.ru"));
        }
        for(int i = 1; i < 11; ++i) {
            accountService.singUp(new UserProfile("player" + String.valueOf(i), "123456", "qwe@mail.ru"));
        }

        Frontend front_login = new LoginServletImpl(accountService);
        Frontend front_register = new RegisterServletImpl(accountService);
        Frontend front_logout = new LogoutServletImpl(accountService);
        Frontend front_checkAuth = new CheckAuthServletImpl(accountService);
        Frontend front_game = new GameServletImpl(accountService, gameMechanics);

        Servlet login = (Servlet) front_login;
        Servlet register = (Servlet) front_register;
        Servlet logout = (Servlet) front_logout;
        Servlet checkAuth  = (Servlet) front_checkAuth;
        Servlet game = (Servlet) front_game;
        Servlet admin = new AdminServlet(accountService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(login), LoginServletImpl.PAGE_URL);
        context.addServlet(new ServletHolder(register), RegisterServletImpl.PAGE_URL);
        context.addServlet(new ServletHolder(logout), LogoutServletImpl.PAGE_URL);
        context.addServlet(new ServletHolder(checkAuth), CheckAuthServletImpl.PAGE_URL);
        context.addServlet(new ServletHolder(admin), AdminServlet.PAGE_URL);
        context.addServlet(new ServletHolder(game), GameServletImpl.PAGE_URL);
        context.addServlet(new ServletHolder(new WebSocketGameServlet(accountService, gameMechanics, webSocketService)), "/gameplay");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true); //!!!
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();
        gameMechanics.run();
    }
}