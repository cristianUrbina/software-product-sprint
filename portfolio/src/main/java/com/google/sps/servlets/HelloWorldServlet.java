package com.google.sps.servlets;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Handles requests sent to the /hello URL. Try running a server and navigating to /hello! */
@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ArrayList<String> randomPhrases = new ArrayList<String>();
    randomPhrases.add("I don't like cake.");
    randomPhrases.add("I love bread.");
    randomPhrases.add("My favorite TV show is Sponge Bob.");
    randomPhrases.add("Architects is my favorite band.");
    randomPhrases.add("Victor Wooten is my favorite bassist.");
    randomPhrases.add("My favorite programming language is C++.");
    randomPhrases.add("I've been using vim for a while and it is amazing.");
    randomPhrases.add("Green is my favorite color.");
    String json = toJson(randomPhrases);
    response.setContentType("application/json");
    response.getWriter().println(json);
  }

  private String toJson(ArrayList<String> list){
    Gson gson = new Gson();
    String json = gson.toJson(list);
    return json;
  }
}
