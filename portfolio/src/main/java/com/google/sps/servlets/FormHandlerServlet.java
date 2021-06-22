package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String first_name = Jsoup.clean(request.getParameter("first-name"), Whitelist.none());
    String last_name = Jsoup.clean(request.getParameter("last-name"), Whitelist.none());
    String email = Jsoup.clean(request.getParameter("email"), Whitelist.none());
    String phone = Jsoup.clean(request.getParameter("phone"), Whitelist.none());

    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Contact-info");
    FullEntity taskEntity =
        Entity.newBuilder(keyFactory.newKey())
            .set("first-name", first_name)
            .set("last-name", last_name)
            .set("email", email)
            .set("phone", phone)
            .build();
    datastore.put(taskEntity);

    response.sendRedirect("/index.html");
    // Get the value entered in the form.
    // String firstName = request.getParameter("first-name");
    // String lastName = request.getParameter("last-name");
    // String email = request.getParameter("email");
    // String phone = request.getParameter("phone");

    // Print the value so you can see it in the server logs.
    System.out.println("First name: "+ first_name);
    System.out.println("Last name: "+ last_name);
    System.out.println("Email: " + email);
    System.out.println("Phone: "+ phone);

    // Write the value to the response so the user can see it.
    // response.sendRedirect("https://curbinaherrera-sps-summer21.uc.r.appspot.com/");
  }
}