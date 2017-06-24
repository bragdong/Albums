package testSpark;

//import static spark.Spark.get;
import static spark.Spark.*;

import static spark.Spark.port;

public class TestSpark {

    public static void main(String[] args) {

        staticFiles.location("/public");  //create under source(src)/public (directory)  /static
        port(3000);
        System.out.println("static files");

        get("/hello", (req, res) -> {
            System.out.println("request made");
            System.out.println(req.queryParams("b"));
            return "hi world2";
        });
        
        get("/hello/:name", (request, response) -> {
            return "Hello: " + request.params(":name");
        });

    }

}
