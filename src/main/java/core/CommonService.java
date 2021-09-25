package core;

import static org.hamcrest.Matchers.lessThan;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.ResponseSpecification;
import java.util.Map;
import org.apache.http.HttpStatus;

public class CommonService {

    protected Method requestMethod;
    protected Map<String, String> parameters;

    public CommonService(Method requestMethod, Map<String, String> queryParams) {
        this.requestMethod = requestMethod;
        this.parameters = queryParams;
    }

    public static ResponseSpecification goodResponseSpecification() {
        return new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectResponseTime(lessThan(10000L))
            .expectStatusCode(HttpStatus.SC_OK)
            .build();
    }

    public static ResponseSpecification badResponseSpecification() {
        return new ResponseSpecBuilder()
            .expectContentType(ContentType.TEXT)
            .expectResponseTime(lessThan(10000L))
            .expectStatusCode(HttpStatus.SC_NOT_FOUND)
            .build();
    }


}
