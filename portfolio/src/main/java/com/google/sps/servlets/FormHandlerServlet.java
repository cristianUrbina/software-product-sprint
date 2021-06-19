package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Get the value entered in the form.
    String firstName = request.getParameter("first-name");
    String lastName = request.getParameter("last-name");
    String email = request.getParameter("email");
    String phone = request.getParameter("phone");

    // Print the value so you can see it in the server logs.
    System.out.println("First name: "+ firstName);
    System.out.println("Last name: "+ lastName);
    System.out.println("Email: " + email);
    System.out.println("Phone: "+ phone);

    // Write the value to the response so the user can see it.
    response.sendRedirect("https://curbinaherrera-sps-summer21.uc.r.appspot.com/");
  }
}