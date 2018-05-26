package com.mgreis.delivery.rsc;

import com.google.common.collect.ImmutableMap;
import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.core.FeatureStore;
import org.ff4j.core.FlippingExecutionContext;
import org.ff4j.core.FlippingStrategy;
import org.ff4j.store.InMemoryFeatureStore;
import org.ff4j.strategy.BlackListStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for {@link ToggleResource}.
 * @author mario.pereira
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ToggleResource.class)
public class ToggleResourceTest {
    /**
     * The mocked model view control.
     */
    @Autowired
    private MockMvc mvc;

    /**
     * The Mocked {@link FF4j} bean.
     */
    @MockBean
    private FF4j ff4j;

    /**
     * Given an instance of the {@link Feature} class, when we call the getToggles method, a JSON string,
     * featuring a representation of that object is returned.
     *
     * @throws Exception If something goes wrong an exception is thrown
     */
    @Test
    public void givenFeature_whenGetToggles_thenReturnJson()
        throws Exception {

        final Feature feature = new Feature("12345");

        final Map<String, Feature> allFeatures = ImmutableMap.of("12345", feature);

        given(ff4j.getFeatures()).willReturn(allFeatures);

        mvc.perform(get("/api/toggle")
                        .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.feature", hasSize(1)))
            .andExpect(jsonPath("$.feature[0].identifier", is(feature.getUid())))
            .andExpect(jsonPath("$.feature[0].active", is(feature.isEnable())));
    }

    /**
     * Given an instance of the {@link Feature} class, when we call the postToggles method, a 201 HTTP code.
     *
     * @throws Exception If something goes wrong an exception is thrown
     */
    @Test
    public void givenFeature_whenPostToggle_thenReturnJson()
        throws Exception {
        final FlippingStrategy flippingStrategy = new BlackListStrategy("user");
        final Feature feature = new Feature("12342:1.32",true);
        feature.setFlippingStrategy(flippingStrategy);

        final Map<String, Feature> allFeatures = ImmutableMap.of("12342", feature);

        given(ff4j.getFeatures()).willReturn(allFeatures);

        mvc.perform(post("/api/toggle")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"toggle\":\"12342\",\"version\":\"1.32\",\"active\":true ,\"blacklist\":\"user\"}"))
        .andExpect(status().isCreated());

        mvc.perform(get("/api/toggle")
                        .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.feature", hasSize(1)))
            .andExpect(jsonPath("$.feature[0].identifier", is(feature.getUid())))
            .andExpect(jsonPath("$.feature[0].active", is(feature.isEnable())));
    }

    /**
     * When we call the putToggle method, a 503 HTTP code is returned.
     *
     * @throws Exception If something goes wrong an exception is thrown
     */
    @Test
    public void whenPutToggle_thenReturn503()
        throws Exception {

        mvc.perform(put("/api/toggle")
                        .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isServiceUnavailable());
    }

    /**
     * When we call the deleteToggle method, a 503 HTTP code is returned.
     *
     * @throws Exception If something goes wrong an exception is thrown
     */
    @Test
    public void whenDeleteToggle_thenReturn503()
        throws Exception {

        mvc.perform(delete("/api/toggle")
                        .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isServiceUnavailable());
    }

    /**
     * Given an instance of the {@link Feature} class, when we call the getToggleVersion() method, a JSON string,
     * featuring a representation of that object is returned.
     *
     * @throws Exception If something goes wrong an exception is thrown
     */
    @Test
    public void givenFeature_whenGetToggleVersion_thenReturnJson()
        throws Exception {
        final String uid = "12345:1.2.3";
        final Feature feature = new Feature(uid);

        given(ff4j.getFeature(uid)).willReturn(feature);

        mvc.perform(get("/api/toggle/12345/version/1.2.3/")
                        .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.feature", hasSize(1)))
            .andExpect(jsonPath("$.feature[0].identifier", is(feature.getUid())))
            .andExpect(jsonPath("$.feature[0].active", is(feature.isEnable())));
    }

    /**
     * When we call the postToggleVersion method, a 503 HTTP code is returned.
     *
     * @throws Exception If something goes wrong an exception is thrown
     */
    @Test
    public void whenPostToggleVersion_thenReturn503()
        throws Exception {

        mvc.perform(post("/api/toggle/12345/version/1.2.3/")
                        .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isServiceUnavailable());
    }

    /**
     * When we call the deleteToggleVersion method, a 204 HTTP code is returned.
     *
     * @throws Exception If something goes wrong an exception is thrown
     */
    @Test
    public void givenFeatureExists_whenPostToggleVersion_thenReturn204()
        throws Exception {

        final String uid = "12345:1.2.3";
        final Feature feature = new Feature(uid);

        given(ff4j.exist(anyString())).willReturn(true);
        given(ff4j.getFeature(anyObject())).willReturn(feature);


        mvc.perform(delete("/api/toggle/12345/version/1.2.3")
                        .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    /**
     * When we call the deleteToggleVersion method and the feature does not exist, a 404 HTTP code is returned.
     *
     * @throws Exception If something goes wrong an exception is thrown
     */
    @Test
    public void givenFeatureNotExists_whenPostToggleVersion_thenReturn204()
        throws Exception {

        final String uid = "12345:1.2.3";
        final Feature feature = new Feature(uid);

        given(ff4j.exist(anyString())).willReturn(false);
        given(ff4j.getFeature(anyObject())).willReturn(feature);


        mvc.perform(delete("/api/toggle/12345/version/1.2.4")
                        .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    /**
     * Given a black listed service, when we call the getToggleVersionService() method, a JSON string,
     * stating that the service is inactive.
     *
     * @throws Exception If something goes wrong an exception is thrown
     */
    @Test
    public void givenActiveToggleServiceIsBlacklisted_whenGetToggleVersionService_thenReturnServiceInactive()
        throws Exception {
        final String uid = "12345:1.2.3";
        final BlackListStrategy flippingStrategy = new BlackListStrategy("user");

        final Feature feature = new Feature(
            uid,
            true,
            "description",
            "12345",
            null,
            flippingStrategy
        );
        given(ff4j.exist(anyString())).willReturn(true);
        given(ff4j.getFeature(anyObject())).willReturn(feature);

        mvc.perform(get("/api/toggle/12345/version/1.2.3/service/user")
                        .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.identifier", is(feature.getUid())))
            .andExpect(jsonPath("$.active", is(false)));
    }

    /**
     * Given an inactive toggle and a black listed service, when we call the getToggleVersionService() method, a JSON string,
     * stating that the service is active.
     *
     * @throws Exception If something goes wrong an exception is thrown
     */
    @Test
    public void givenInaActiveToggleServiceIsBlacklisted_whenGetToggleVersionService_thenReturnServiceInactive()
        throws Exception {
        final String uid = "12345:1.2.3";
        final BlackListStrategy flippingStrategy = new BlackListStrategy("service");

        final Feature feature = new Feature(
            uid,
            true,
            "description",
            "12345",
            null,
            flippingStrategy
        );
        FeatureStore store = new InMemoryFeatureStore();
        store.create(feature);

        final FlippingExecutionContext flippingExecutionContext = new FlippingExecutionContext();
        flippingExecutionContext.addValue(BlackListStrategy.CLIENT_HOSTNAME, "user");

        given(ff4j.exist(anyString())).willReturn(true);
        given(ff4j.getFeature(anyString())).willReturn(feature);
        given(ff4j.check(anyString(),anyObject()))
            .willReturn(flippingStrategy.evaluate(uid,store,flippingExecutionContext));

        mvc.perform(get("/api/toggle/12345/version/1.2.3/service/user"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.identifier", is(feature.getUid())))
            .andExpect(jsonPath("$.active", is(true)));
    }




}
