package integration;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.util.*;

public class MyClient {

    public static MyClient getClient() {
        if (System.getenv("stage") == "beta") {
            System.out.println("Logging: stage, hostnamme" + betaClient.stage + "," + betaClient.hostname);
            return betaClient;
        } else {
            System.out.println("Logging: stage, hostnamme" + alphaClient.stage + "," + alphaClient.hostname);
            return alphaClient;
        }
    }

    private static final MyClient alphaClient;
    private static final MyClient betaClient;
    static {
        alphaClient = new MyClient("kl4wly89ia.execute-api.us-east-1.amazonaws.com");
        betaClient = new MyClient("lqf9lm2krc.execute-api.us-east-1.amazonaws.com");
    }

    private final String stage;
    private final String hostname;
    private final HttpClient httpClient;

    private MyClient(String hostname) {
        this.stage = Optional.ofNullable(System.getenv("stage")).
                orElse(Optional.ofNullable(System.getProperty("stage"))
                        .orElse("alpha"));
        this.hostname = hostname;
        this.httpClient = HttpClientBuilder.create().build();
    }

    private HttpHost getTarget() {
        return new HttpHost(hostname, 443, "https");
    }

    private HttpGet getGetRequest(String accountId) {
        HttpGet httpGet = new HttpGet(String.format("/%s/accounts/%s/transactions", stage, accountId));
        httpGet.addHeader("User-Agent", "MyApp");
        return httpGet;
    }

    private HttpPost getPostRequest(String accountId, float amount, String transactionId) throws Exception {
        HttpPost httpPost = new HttpPost(String.format("/%s/accounts/%s/transactions", stage, accountId));
        httpPost.addHeader("User-Agent", "MyApp");
        JSONObject holder = new JSONObject();
        Double amountVal = (double) amount;
        holder.put("amount", amountVal);
        holder.put("transaction_id", transactionId);
        System.out.println("entity: " + holder.toString());
        StringEntity stringEntity = new StringEntity(holder.toString());
        httpPost.setEntity(stringEntity);
        httpPost.addHeader("Content-Type","application/json");
        return httpPost;
    }

    public HttpResponse getTransactions(String accountId) throws Exception {
        return httpClient.execute(getTarget(), getGetRequest(accountId));
    }

    public HttpResponse postTransaction(String accountId, float amount, String transactionId) throws Exception {
        return httpClient.execute(getTarget(), getPostRequest(accountId, amount, transactionId));
    }
}
