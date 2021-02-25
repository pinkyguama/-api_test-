package specifications;

import helpers.DataHelper;
import helpers.RequestHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import javax.xml.crypto.Data;

public class requestSpecs {
    public static RequestSpecification generateToken(){
        String token = RequestHelper.getUserToken();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeader("Authorization","Bearer "+ token );
        return requestSpecBuilder.build();
    }
    public static RequestSpecification generateFakeToken(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeader("Authorization", "Beasadrer wrongtoken");
        return requestSpecBuilder.build();
    };
}

