package integration;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class CreateAndGetTransactionsIT {

    @Test
    public void createTransactionShouldReturnInGetTransactions() throws Exception {
        MyClient myClient = MyClient.getClient();
        String accountId = "1234";
        String transactionId = UUID.randomUUID().toString();

        HttpResponse httpPostResponse = myClient.postTransaction(accountId, 10, transactionId);
        Assert.assertEquals(200, httpPostResponse.getStatusLine().getStatusCode());

        HttpResponse httpGetResponse = myClient.getTransactions(accountId);
        Assert.assertEquals(200, httpGetResponse.getStatusLine().getStatusCode());

        String encoding;
        try {
            encoding = httpGetResponse.getEntity().getContentEncoding().getValue();
            encoding = encoding == null ? "UTF-8" : encoding;
        } catch (NullPointerException e) {
            encoding = "UTF-8";
        }

        String body = IOUtils.toString(httpGetResponse.getEntity().getContent(), encoding);

        Assert.assertTrue(body.contains(transactionId));
    }
}
