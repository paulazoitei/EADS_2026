package org.example.week1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {

        String method = request.getMethod();
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String languages = request.getHeader("Accept-Language");
        String value = request.getParameter("value");
        String client = request.getParameter("client");

        System.out.println("----- Request received -----");
        System.out.println("HTTP method: " + method);
        System.out.println("Client IP: " + ip);
        System.out.println("User-Agent: " + userAgent);
        System.out.println("Client languages: " + languages);
        System.out.println("Parameter value: " + value);
        System.out.println("--------------------------");

        if (value == null || (!value.equals("1") && !value.equals("2"))) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "The parameter value must be either 1 or 2"
            );
            return;
        }


        if ("desktop".equalsIgnoreCase(client)) {
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().println(
                    "The value received is : " + value
            );
            return;
        }


        if (value.equals("1")) {
            request.getRequestDispatcher("/page1.html")
                    .forward(request, response);
        } else {
            request.getRequestDispatcher("/page2.html")
                    .forward(request, response);
        }
    }
}