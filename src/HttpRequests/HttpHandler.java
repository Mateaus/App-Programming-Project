package HttpRequests;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.List;

public class HttpHandler {

    private String UrlRequest;
    private List<NameValuePair> nameValuePairs;

    public HttpHandler(String UrlRequest, List<NameValuePair> nameValuePairs) {
        this.UrlRequest = UrlRequest;
        this.nameValuePairs = nameValuePairs;
    }

    public HttpPost HttpPostRequest() {
    	try {
    		HttpPost httpPost = new HttpPost(this.UrlRequest);
    		httpPost.setEntity(new UrlEncodedFormEntity(this.nameValuePairs));
    		return httpPost;
    	} catch (Exception e ) {
    		System.out.println(e);
    		return null;
    	}

    }

    public HttpResponse HttpResponseRequest(HttpPost httpPost) {
    	try {
    		HttpClient httpClient = HttpClientBuilder.create().build();
    		HttpResponse httpResponse = httpClient.execute(httpPost);
    		return httpResponse;
    	} catch (Exception e ) {
    		System.out.println(e);
    		return null;
    	}
    }
}
