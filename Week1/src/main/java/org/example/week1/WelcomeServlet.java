package org.example.week1;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.println("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Welcome</title>
                </head>
                <body>
                    <h1>You are welcome!</h1>
                    <p>Please choose 1 or 2:</p>

                    <form method="post" action="controller">
                        <label>
                            <input type="radio" name="value" value="1" required>
                            Value 1
                        </label>
                        <br>

                        <label>
                            <input type="radio" name="value" value="2">
                            Value 2
                        </label>
                        <br><br>

                        <button type="submit">Send</button>
                    </form>
                </body>
                </html>
                """);
    }
}