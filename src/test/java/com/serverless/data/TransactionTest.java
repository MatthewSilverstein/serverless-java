package com.serverless.data;

import org.junit.Assert;
import org.junit.Test;

public class TransactionTest {

    @Test
    public void setAccount_idShouldSetAccountId() {
        Transaction transaction = new Transaction();
        String expectedAccountId = "accountId";
        transaction.setAccount_id(expectedAccountId);
        String actualAccountId = transaction.getAccount_id();
        Assert.assertEquals(expectedAccountId, actualAccountId);
    }
}
