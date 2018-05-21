package com.mgreis.delivery;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.core.FlippingExecutionContext;
import org.ff4j.strategy.ClientFilterStrategy;
import org.ff4j.web.jersey2.store.FeatureStoreHttp;
import org.ff4j.web.jersey2.store.PropertyStoreHttp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

/**
 * A Class containing a test battery using {@link FF4j} as an HTTP client.
 * These tests require that our server is running, they are skipped by default.
 *
 * @author Mario Pereira
 * @since 1.0.0-SNAPSHOT
 */
public class RestApiClientTest {
    /**
     * An instance of the {@link FF4j} class.
     */
    private static FF4j FF4J;

    /**
     * The ReST API URL.
     */
    private final static String REST_API_URL= "http://localhost:8080/api/ff4j/";


    @BeforeClass
    public static void setup() {
        // Init FF4j as HTTP CLIENT
        FF4J = new FF4j();
        FF4J.setFeatureStore(new FeatureStoreHttp(REST_API_URL, "user", "userPass"));
        FF4J.setPropertiesStore(new PropertyStoreHttp(REST_API_URL, "user", "userPass"));

    }


    @Test
    public void setupFF4j() {
        Feature f1 = FF4J.getFeatureStore().read("f1");
        System.out.println(f1);
        // EXPECT AUDITING AND SECURITY
    }

    @Test
    public void setupFF4jPostGetDelete() {
        String uuid = UUID.randomUUID().toString();
        FF4J.getFeatureStore().create(new Feature(uuid));
        Feature f1 = FF4J.getFeatureStore().read(uuid);
        System.out.println(f1);
        FF4J.getFeatureStore().delete(uuid);

        // EXPECT AUDITING AND SECURITY

    }

    @Test
    public void testClientFilterStrategy() {
        String uuid = UUID.randomUUID().toString();
        ClientFilterStrategy clientFilterStrategy = new ClientFilterStrategy("user");

        Feature clientFilterFeature = new Feature(uuid, true, null, null, null, clientFilterStrategy);

        FF4J.getFeatureStore().create(clientFilterFeature);

        FlippingExecutionContext flippingExecutionContext = new FlippingExecutionContext();
        flippingExecutionContext.addValue("clientHostName", "user");

        System.out.println(FF4J.check(uuid, flippingExecutionContext));

    }
}
