package net.tekrei.utility;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.apache.http.HttpHeaders.USER_AGENT;

public class HttpUtility {

    public static String executePost(String actionURL, List<NameValuePair> postData) {
        try {
            HttpPost method = new HttpPost(actionURL);
            method.setEntity(new UrlEncodedFormEntity(postData));
            return execute(method);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String executeGet(String actionURL) {
        try {
            return execute(new HttpGet(actionURL));
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private static String execute(HttpRequestBase method) throws IOException {
        method.setHeader("User-Agent", USER_AGENT);
        HttpResponse status = HttpClientBuilder.create().build().execute(method);
        if (status.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            method.releaseConnection();
            return "CONNECTION ERROR";
        } else {
            BufferedReader rd = new BufferedReader(new InputStreamReader(status.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            String response = result.toString();
            method.releaseConnection();
            return response;
        }
    }

}
