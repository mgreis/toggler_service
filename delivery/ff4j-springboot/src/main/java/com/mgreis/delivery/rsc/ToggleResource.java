package com.mgreis.delivery.rsc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgreis.delivery.jsonmodel.FeatureResponse;
import com.mgreis.delivery.jsonmodel.ImmutableFeatureResponse;
import com.mgreis.delivery.jsonmodel.ImmutableToggleServiceResponse;
import com.mgreis.delivery.jsonmodel.ToggleServiceRequest;
import com.mgreis.delivery.jsonmodel.ToggleServiceResponse;
import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.core.FlippingStrategy;
import org.ff4j.strategy.BlackListStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;


/**
 * Feature resource ReST controller class.
 *
 * @author Mario Pereira
 * @since 1.0.0
 */
@RestController
public class ToggleResource {
    /**
     * The object mapper.
     */
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * An {@link Autowired} instance of {@link FF4j}.
     */
    @Autowired
    private FF4j ff4j;

    /**
     * Get All toggles.
     *
     * @return a {@link String} containing the web page.
     */
    @GetMapping(value = "/api/toggle", produces = "application/json")
    public ResponseEntity<?> getToggles() {

        final Map<String, Feature> features = ff4j.getFeatures();

        final ArrayList<FeatureResponse> featuresList = new ArrayList<>();

        features.forEach((s, feature) -> featuresList.add(ImmutableFeatureResponse.builder()
                                                              .identifier(feature.getUid())
                                                              .group(feature.getGroup())
                                                              .strategy(getFeatureStrategy(feature.getFlippingStrategy()))
                                                              .active(feature.isEnable())
                                                              .build()));

        final ToggleServiceResponse response = ImmutableToggleServiceResponse.builder()
            .uuid(UUID.randomUUID().toString())
            .feature(featuresList.toArray(new FeatureResponse[featuresList.size()]))
            .service("")
            .timestamp(Instant.now().getEpochSecond())
            .build();

        try {
            return new ResponseEntity<>(OBJECT_MAPPER.writeValueAsString(response), HttpStatus.OK);
        } catch (final JsonProcessingException exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/api/toggle", produces = "application/json")
    public ResponseEntity<?> postToggle(@RequestBody ToggleServiceRequest toggleServiceRequest) {

        BlackListStrategy flippingStrategy = null;
        boolean active = false;

        if (toggleServiceRequest.getBlacklist() != null) {
            flippingStrategy = new BlackListStrategy(toggleServiceRequest.getBlacklist());
        }

        if (toggleServiceRequest.getActive() != null) {
            active = toggleServiceRequest.getActive().booleanValue();
        }


        final Feature feature = new Feature(
            toggleServiceRequest.getToggle() + ":" + toggleServiceRequest.getVersion(),
            active,
            toggleServiceRequest.getDescription(),
            toggleServiceRequest.getFeature(),
            null,
            flippingStrategy
        );
        ff4j.createFeature(feature);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/toggle")
    public ResponseEntity<?> putToggle() {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @DeleteMapping(value = "/api/toggle")
    public ResponseEntity<?> deleteToggle() {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping(value = "/api/toggle/{toggle}/version/{version}", produces = "application/json")
    public ResponseEntity<?> getToggleVersion(@PathVariable String toggle, @PathVariable String version) {

        Feature feature = ff4j.getFeature(toggle + ":" + version);

        ImmutableFeatureResponse featureResponse = ImmutableFeatureResponse.builder()
            .identifier(feature.getUid())
            .group(feature.getGroup())
            .strategy(getFeatureStrategy(feature.getFlippingStrategy()))
            .active(feature.isEnable())
            .build();

        final ToggleServiceResponse response = ImmutableToggleServiceResponse.builder()
            .uuid(UUID.randomUUID().toString())
            .feature(featureResponse)
            .service("")
            .timestamp(Instant.now().getEpochSecond())
            .build();

        try {
            return new ResponseEntity<>(OBJECT_MAPPER.writeValueAsString(response), HttpStatus.OK);
        } catch (final JsonProcessingException exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/api/toggle/{toggle}/version/{version}")
    public ResponseEntity<?> postToggleVersion() {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @DeleteMapping(value = "/api/toggle/{toggle}/version/{version}")
    public ResponseEntity<?> deleteToggleVersion(@PathVariable String toggle, @PathVariable String version) {
        if (ff4j.delete(toggle + ":" + version) != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String getFeatureStrategy(final FlippingStrategy flippingStrategy) {
        if (flippingStrategy == null) {
            return null;
        } else return flippingStrategy.toString();

    }


}