package LiveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.HashMap;
import java.util.Map;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class GitHubProject {

    //Request and Response specification variables
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    String sshKey;
    int sshKeyID;

    @BeforeClass
    public void setUp(){
        //request specification
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .setBaseUri("https://api.github.com")
                .build();

    }
    @Test(priority = 1)
    public void postRequestTest(){
        //request body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("title" , "TestKey");
        reqBody.put("key", "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCb6OxbLf8s/pqaocuxTahRHagss2aoaXeoFRMlSwM2wRKXrcTe4j7q5wkyYzvmk0KDjhW8MVCeOZyjRbmnHNYcbSNP8hzRYbeuy2RMcXNlSCpgfziN7ivJtTPFurU2YszN83eiJ4jnusnh3z8RBxx3hTlbSx7TjLO9pJxx+dYPNGnnxnfdTmxa+p+zQj0u2k+ZSh+h7ae3tAp7eagVQrZePFhM3sodoXUob33a3FaXvkb/sNDrad1t181Br85gtAxYW9W10IzlQBOTGZlRxv3dYEQiBa0PS/5dsy+gs1CwrTajIVBEu9klrMwGjVCuYtoEgC2gwu/0NC34WB15UNzZ");


        String resourcePath = "user/keys";
        //generating response
        Response response = given().spec(requestSpec).body(reqBody)
                        .when().post(resourcePath);


        sshKeyID = response.then().extract().path("id");
        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);
        //assertion
        response.then().statusCode(201);



    }
    @Test(priority = 2)
    public void getRequestTest(){
        Response response = given().spec(requestSpec).pathParam("keyId",sshKeyID)
                .when().get("/user/keys/{keyId}");
        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);


        //Assersion
        response.then().statusCode(200);
    }

    @Test(priority = 3)
    public void deleteRequestTest(){
        Response response = given().spec(requestSpec).pathParam("keyId",sshKeyID)
                        .when().delete("/user/keys/{keyId}");
        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);

        //Assersion
        response.then().statusCode(204);
    }


}
