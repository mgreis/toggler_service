package com.mgreis.delivery;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.core.FlippingExecutionContext;
import org.ff4j.strategy.ClientFilterStrategy;
import org.ff4j.web.jersey2.store.FeatureStoreHttp;
import org.ff4j.web.jersey2.store.PropertyStoreHttp;
import org.junit.Test;

import java.util.UUID;

/**
 * Sample tests to work with REST API.
 *
 * @author Mario Pereira
 * @since 1.0.0-SNAPSHOT
 */
public class SampleRestApiClientTest {

    private FF4j ff4j;

    @Test
    public void setupFF4j() {

        String targetRestApiURL = "http://localhost:8080/api/ff4j/";

        // Init FF4j as HTTP CLIENT
        ff4j = new FF4j();
        ff4j.setFeatureStore(new FeatureStoreHttp(targetRestApiURL, "user", "userPass"));
        ff4j.setPropertiesStore(new PropertyStoreHttp(targetRestApiURL, "user", "userPass"));

        Feature f1 = ff4j.getFeatureStore().read("f1");
        System.out.println(f1);

        // EXPECT AUDITING AND SECURITY

    }

    @Test
    public void setupFF4jPostGetDelete() {

        String targetRestApiURL = "http://localhost:8080/api/ff4j/";

        // Init FF4j as HTTP CLIENT
        ff4j = new FF4j();
        ff4j.setFeatureStore(new FeatureStoreHttp(targetRestApiURL, "user", "userPass"));
        ff4j.setPropertiesStore(new PropertyStoreHttp(targetRestApiURL, "user", "userPass"));
        String uuid = UUID.randomUUID().toString();
        ff4j.getFeatureStore().create(new Feature(uuid));
        Feature f1 = ff4j.getFeatureStore().read(uuid);
        System.out.println(f1);
        ff4j.getFeatureStore().delete(uuid);

        // EXPECT AUDITING AND SECURITY

    }

    @Test
    public void testClientFilterStrategy() {

        String targetRestApiURL = "http://localhost:8080/api/ff4j/";

        // Init FF4j as HTTP CLIENT
        ff4j = new FF4j();
        ff4j.setFeatureStore(new FeatureStoreHttp(targetRestApiURL, "user", "userPass"));
        ff4j.setPropertiesStore(new PropertyStoreHttp(targetRestApiURL, "user", "userPass"));
        String uuid = UUID.randomUUID().toString();
        ClientFilterStrategy clientFilterStrategy = new ClientFilterStrategy("user");

        Feature clientFilterFeature = new Feature(uuid, true, null, null, null, clientFilterStrategy);

        ff4j.getFeatureStore().create(clientFilterFeature);

        FlippingExecutionContext flippingExecutionContext = new FlippingExecutionContext();
        flippingExecutionContext.addValue("clientHostName", "user");

        System.out.println(ff4j.check(uuid, flippingExecutionContext));

    }
}
