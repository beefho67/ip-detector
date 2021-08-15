package example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class IpDetectorLambdaTest {

    private static InputStream input;
    JSONParser parser = new JSONParser();
    
    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
    	String testStr = "{\"path\":\"\\/detectip\",\"headers\":{\"Accept\":\"*\\/*\",\"CloudFront-Viewer-Country\":\"TW\",\"CloudFront-Forwarded-Proto\":\"https\",\"CloudFront-Is-Tablet-Viewer\":\"false\",\"origin\":\"https:\\/\\/d3ucfn7czb26to.cloudfront.net\",\"CloudFront-Is-Mobile-Viewer\":\"false\",\"Referer\":\"https:\\/\\/d3ucfn7czb26to.cloudfront.net\\/index.html\",\"User-Agent\":\"Mozilla\\/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/73.0.3683.86 Safari\\/537.36\",\"X-Forwarded-Proto\":\"https\",\"CloudFront-Is-SmartTV-Viewer\":\"false\",\"Host\":\"171nk3xfcb.execute-api.ap-northeast-1.amazonaws.com\",\"Accept-Encoding\":\"gzip, deflate, br\",\"X-Forwarded-Port\":\"443\",\"X-Amzn-Trace-Id\":\"Root=1-5ca9ea2a-7e667630ab440962870346c0\",\"Via\":\"2.0 bd10c7cefd8295a0ff40302b0e4b3977.cloudfront.net (CloudFront)\",\"Authorization\":\"allow\",\"X-Amz-Cf-Id\":\"cualE87-mm1OV_ycBtL5JGk-G4uB4xQzUk4aRZ6J5Tplffv2dQVx0g==\",\"X-Forwarded-For\":\"218.161.9.242, 52.46.57.101\",\"Accept-Language\":\"en-US,en;q=0.9,ko;q=0.8,ja;q=0.7,zh-TW;q=0.6,zh-CN;q=0.5,zh;q=0.4,vi;q=0.3,id;q=0.2\",\"CloudFront-Is-Desktop-Viewer\":\"true\"},\"pathParameters\":null,\"isBase64Encoded\":false,\"multiValueQueryStringParameters\":null,\"multiValueHeaders\":{\"Accept\":[\"*\\/*\"],\"CloudFront-Viewer-Country\":[\"TW\"],\"CloudFront-Forwarded-Proto\":[\"https\"],\"CloudFront-Is-Tablet-Viewer\":[\"false\"],\"origin\":[\"https:\\/\\/d3ucfn7czb26to.cloudfront.net\"],\"CloudFront-Is-Mobile-Viewer\":[\"false\"],\"Referer\":[\"https:\\/\\/d3ucfn7czb26to.cloudfront.net\\/index.html\"],\"User-Agent\":[\"Mozilla\\/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/73.0.3683.86 Safari\\/537.36\"],\"X-Forwarded-Proto\":[\"https\"],\"CloudFront-Is-SmartTV-Viewer\":[\"false\"],\"Host\":[\"171nk3xfcb.execute-api.ap-northeast-1.amazonaws.com\"],\"Accept-Encoding\":[\"gzip, deflate, br\"],\"X-Forwarded-Port\":[\"443\"],\"X-Amzn-Trace-Id\":[\"Root=1-5ca9ea2a-7e667630ab440962870346c0\"],\"Via\":[\"2.0 bd10c7cefd8295a0ff40302b0e4b3977.cloudfront.net (CloudFront)\"],\"Authorization\":[\"allow\"],\"X-Amz-Cf-Id\":[\"cualE87-mm1OV_ycBtL5JGk-G4uB4xQzUk4aRZ6J5Tplffv2dQVx0g==\"],\"X-Forwarded-For\":[\"218.161.9.242, 52.46.57.101\"],\"Accept-Language\":[\"en-US,en;q=0.9,ko;q=0.8,ja;q=0.7,zh-TW;q=0.6,zh-CN;q=0.5,zh;q=0.4,vi;q=0.3,id;q=0.2\"],\"CloudFront-Is-Desktop-Viewer\":[\"true\"]},\"requestContext\":{\"resourceId\":\"v571r5\",\"authorizer\":{\"principalId\":\"xxxx\",\"integrationLatency\":56},\"resourcePath\":\"\\/detectip\",\"httpMethod\":\"GET\",\"extendedRequestId\":\"XxGGnHOVNjMFkgA=\",\"requestTime\":\"07\\/Apr\\/2019:12:16:42 +0000\",\"path\":\"\\/prod\\/detectip\",\"accountId\":\"177683156638\",\"protocol\":\"HTTP\\/1.1\",\"stage\":\"prod\",\"domainPrefix\":\"171nk3xfcb\",\"requestTimeEpoch\":1554639402321,\"requestId\":\"00cbecc5-592f-11e9-8884-db3b010da42b\",\"identity\":{\"cognitoIdentityPoolId\":null,\"accountId\":null,\"cognitoIdentityId\":null,\"caller\":null,\"sourceIp\":\"218.161.9.242\",\"accessKey\":null,\"cognitoAuthenticationType\":null,\"cognitoAuthenticationProvider\":null,\"userArn\":null,\"userAgent\":\"Mozilla\\/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/73.0.3683.86 Safari\\/537.36\",\"user\":null},\"domainName\":\"171nk3xfcb.execute-api.ap-northeast-1.amazonaws.com\",\"apiId\":\"171nk3xfcb\"},\"resource\":\"\\/detectip\",\"httpMethod\":\"GET\",\"queryStringParameters\":null,\"stageVariables\":null,\"body\":null}"; 
        input = new ByteArrayInputStream(testStr.getBytes());
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("detectip");

        return ctx;
    }

    @Test
    public void testIpDetectorLambda() {
    	IpDetectorLambdaHandler handler = new IpDetectorLambdaHandler();
        Context ctx = createContext();
        
        try {
        	ByteArrayOutputStream output = new ByteArrayOutputStream();
			handler.handleRequest(input, output, ctx);
			String out = new String(output.toByteArray(), "UTF-8");
			JSONObject result = (JSONObject) parser.parse(out);
			String bodyStr = (String)result.get("body");
			JSONObject body = (JSONObject) parser.parse(bodyStr);
			// TODO: validate output here if needed.
	        Assert.assertEquals("218.161.9.242", body.get("yourIp"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
    }
}
