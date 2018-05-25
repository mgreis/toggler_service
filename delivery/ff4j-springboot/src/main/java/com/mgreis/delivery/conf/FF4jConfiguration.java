package com.mgreis.delivery.conf;

import org.ff4j.FF4j;
import org.ff4j.cassandra.CassandraConnection;
import org.ff4j.cassandra.store.EventRepositoryCassandra;
import org.ff4j.cassandra.store.FeatureStoreCassandra;
import org.ff4j.cassandra.store.PropertyStoreCassandra;
import org.ff4j.utils.Util;
import org.ff4j.web.ApiConfig;
import org.ff4j.web.FF4jDispatcherServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for {@link FF4j}.
 *
 * @author Mario Pereira
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass({FF4j.class})
@ComponentScan(value = {"org.ff4j.spring.boot.web.api", "org.ff4j.services", "org.ff4j.aop", "org.ff4j.spring", "org.ff4j.cassandra.CassandraConnection"})
public class FF4jConfiguration {

    /**
     * Flag that checks whether or not authentication is required.
     * It receives its value from the application.resources file in the resources directory.
     */
    @Value("${ff4j.webapi.authentication}")
    private final boolean authentication = false;

    /**
     * Flag that checks whether or not authorization is required.
     * It receives its value from the application.resources file in the resources directory.
     */
    @Value("${ff4j.webapi.authorization}")
    private final boolean authorization = true;

    /**
     * The cassandra hostname.
     * It receives its value from the application.resources file in the resources directory.
     */
    @Value("${ff4j.cassandra.host}")
    private final String cassandraHost = "cassandra";

    /**
     * The cassandra port.
     * It receives its value from the application.resources file in the resources directory.
     */
    @Value("${ff4j.cassandra.port}")
    private final int cassandraPort = 9042;

    /**
     * Admin username.
     * NOT MEANT FOR PRODUCTION.
     * It receives its value from the application.resources file in the resources directory.
     */
    @Value("${ff4j.security.user.name}")
    private final String username = "ff4j";

    /**
     * The password.
     * NOT MEANT FOR PRODUCTION.
     * It receives its value from the application.resources file in the resources directory.
     */
    @Value("${ff4j.security.user.password}")
    private final String password = "ff4j";

    /**
     * The api key.
     * NOT MEANT FOR PRODUCTION.
     * It receives its value from the application.resources file in the resources directory.
     */
    @Value("${ff4j.security.api.key}")
    private final String apiKey = "api_key";

    /**
     * The server context path.
     * It receives its value from the application.resources file in the resources directory.
     */
    @Value("${server.api.contextPath}")
    private final String serverContextPath = "/api";

    /**
     * The server port.
     * It receives its value from the application.resources file in the resources directory.
     */
    @Value("${server.port}")
    private final int serverPort = 8080;



    /**
     * Initialize {@link FF4j}.
     *
     * @return an initialized instance of {@link FF4j}
     */
    @Bean
    public FF4j getFF4j() {
        final CassandraConnection conn = new CassandraConnection(cassandraHost, cassandraPort);
        conn.createKeySpace();

        final FF4j ff4j = new FF4j();
        ff4j.setFeatureStore(new FeatureStoreCassandra(conn));
        ff4j.setPropertiesStore(new PropertyStoreCassandra(conn));
        ff4j.setEventRepository(new EventRepositoryCassandra(conn));
        ff4j.createSchema();
        ff4j.audit(false);
        return ff4j;
    }

    /**
     * Initialize {@link FF4jDispatcherServlet}.
     *
     * @return an initialized instance of {@link FF4jDispatcherServlet}
     */
    @Bean
    public FF4jDispatcherServlet getFF4JServlet() {
        FF4jDispatcherServlet ds = new FF4jDispatcherServlet();
        ds.setFf4j(getFF4j());
        return ds;
    }

    /**
     * Initialize {@link ApiConfig}.
     *
     * @return an initialized instance of {@link ApiConfig}
     */
    @Bean
    public ApiConfig getApiConfig() {
        ApiConfig apiConfig = new ApiConfig();

        //NOT MEANT FOR PRODUCTION
        apiConfig.setAuthenticate(authentication);
        apiConfig.setAutorize(authorization);

        apiConfig.createApiKey(apiKey, true, true, Util.set("ADMIN", "USER"));
        apiConfig.createUser(username, password, true, true, Util.set("ADMIN", "USER"));

        apiConfig.setWebContext(serverContextPath);
        apiConfig.setPort(serverPort);
        apiConfig.setFF4j(getFF4j());
        return apiConfig;
    }

}
