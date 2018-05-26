package com.mgreis.delivery.rsc;

import com.mgreis.delivery.jsonmodel.CheckServiceResponse;
import com.mgreis.delivery.jsonmodel.FeatureRepresentation;
import com.mgreis.delivery.jsonmodel.ImmutableCheckServiceResponse;
import com.mgreis.delivery.jsonmodel.ImmutableFeatureRepresentation;
import com.mgreis.delivery.jsonmodel.ImmutableToggleServiceResponse;
import com.mgreis.delivery.jsonmodel.ToggleServiceRequest;
import com.mgreis.delivery.jsonmodel.ToggleServiceResponse;
import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.core.FlippingExecutionContext;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Toggle resource ReST controller class.
 *
 * @author Mario Pereira
 * @since 1.0.0
 */
@RestController
public class ToggleResource {
    /**
     * An {@link Autowired} instance of {@link FF4j}.
     */
    @Autowired
    private FF4j ff4j;

    /**
     * Get All toggles.
     *
     * @return a {@link ToggleServiceResponse} class instance containing all the toggles.
     */
    @GetMapping(value = "/api/toggle", produces = "application/json")
    public ResponseEntity<ToggleServiceResponse> getToggles() {

        final Map<String, Feature> features = ff4j.getFeatures();

        final ArrayList<FeatureRepresentation> featuresList = new ArrayList<>();

        features.forEach((s, feature) -> featuresList.add(ImmutableFeatureRepresentation.builder()
                                                              .identifier(feature.getUid())
                                                              .group(feature.getGroup())
                                                              .strategy(getFeatureStrategy(feature.getFlippingStrategy()))
                                                              .active(feature.isEnable())
                                                              .build()));

        final ToggleServiceResponse response = ImmutableToggleServiceResponse.builder()
            .uuid(UUID.randomUUID().toString())
            .featureRepresentation(featuresList.toArray(new FeatureRepresentation[featuresList.size()]))
            .service("")
            .timestamp(Instant.now().getEpochSecond())
            .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }





    /**
     * Insert a new toggle into the system.
     *
     * @param toggleServiceRequest An instance of {@link ToggleServiceRequest} featuring the necessary data.
     * @return a
     */
    @PostMapping(value = "/api/toggle", produces = "application/json")
    public ResponseEntity<?> postToggle(@RequestBody final ToggleServiceRequest toggleServiceRequest) {

        BlackListStrategy flippingStrategy = null;
        boolean active = false;

        if (toggleServiceRequest.getBlacklist() != null) {
            flippingStrategy = new BlackListStrategy(toggleServiceRequest.getBlacklist());
        }

        if (toggleServiceRequest.getActive() != null) {
            active = toggleServiceRequest.getActive();
        }


        final Feature feature = new Feature(
            toggleServiceRequest.getToggle() + ":" + toggleServiceRequest.getVersion(),
            active,
            toggleServiceRequest.getDescription(),
            toggleServiceRequest.getToggle(),
            null,
            flippingStrategy
        );
        ff4j.createFeature(feature);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Update all toggles. Not implemented.
     *
     * @return A {@link ResponseEntity} with the service unavailable HTTP code.
     */
    @PutMapping(value = "/api/toggle")
    public ResponseEntity<?> putToggle() {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Delete all toggles. Not implemented.
     *
     * @return A {@link ResponseEntity} with the service unavailable HTTP code.
     */
    @DeleteMapping(value = "/api/toggle")
    public ResponseEntity<?> deleteToggle() {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Get a toggle featuring a given toggle version combination.
     *
     * @param toggle  the toggle name
     * @param version the toggle version
     * @return the toggle if it exists, else not found
     */
    @GetMapping(value = "/api/toggle/{toggle}/version/{version}", produces = "application/json")
    public ResponseEntity<ToggleServiceResponse> getToggleVersion(@PathVariable final String toggle,
                                                                  @PathVariable final String version) {

        final Feature feature = ff4j.getFeature(toggle + ":" + version);

        final ImmutableFeatureRepresentation featureRepresentation = ImmutableFeatureRepresentation.builder()
            .identifier(feature.getUid())
            .group(feature.getGroup())
            .strategy(getFeatureStrategy(feature.getFlippingStrategy()))
            .active(feature.isEnable())
            .build();

        final ToggleServiceResponse response = ImmutableToggleServiceResponse.builder()
            .uuid(UUID.randomUUID().toString())
            .featureRepresentation(featureRepresentation)
            .service("")
            .timestamp(Instant.now().getEpochSecond())
            .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Not implemented.
     *
     * @return A {@link ResponseEntity} with the service unavailable HTTP code.
     */
    @PostMapping(value = "/api/toggle/{toggle}/version/{version}")
    public ResponseEntity<?> postToggleVersion() {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }


    /**
     * Delete a toggle instance with a given version.
     *
     * @param toggle  the toggle name
     * @param version the toggle version
     * @return an instance of the {@link ResponseEntity} class featuring an HTTP status.
     */
    @DeleteMapping(value = "/api/toggle/{toggle}/version/{version}")
    public ResponseEntity<?> deleteToggleVersion(@PathVariable String toggle, @PathVariable final String version) {
        if (ff4j.exist(toggle + ':' + version)) {
            ff4j.delete(toggle + ":" + version);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Service unavailable.
     *
     * @return A {@link ResponseEntity} with the service unavailable HTTP code.
     */
    @PutMapping(value = "/api/toggle/version/{version}")
    public ResponseEntity<?> putToggleVersion() {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Check if a toggle of a certain version is active on a given service.
     *
     * @param toggle  the toggle name
     * @param version the toggle version
     * @param service the service name
     * @return return a {@link ResponseEntity} featuring whether or not the service is active.
     */
    @GetMapping(value = "/api/toggle/{toggle}/version/{version}/service/{service}", produces = "application/json")
    public ResponseEntity<CheckServiceResponse> getToggleVersionService(@PathVariable final String toggle,
                                                                        @PathVariable final String version,
                                                                        @PathVariable final String service) {
        final String identifier = toggle + ":" + version;
        if (!ff4j.exist(identifier)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final FlippingExecutionContext flippingExecutionContext = new FlippingExecutionContext();
        flippingExecutionContext.addValue(BlackListStrategy.CLIENT_HOSTNAME, service);

        final CheckServiceResponse response = ImmutableCheckServiceResponse.builder()
            .identifier(identifier)
            .uuid(UUID.randomUUID().toString())
            .active(ff4j.check(identifier, flippingExecutionContext))
            .service(service)
            .timestamp(Instant.now().getEpochSecond())
            .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Insert the service on the black list. Not implemented.
     *
     * @return A {@link ResponseEntity} with the service unavailable HTTP code.
     */
    @PostMapping(value = "/api/toggle/{toggle}version/{version}/service/{service}")
    public ResponseEntity<?> postToggleVersionservice() {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Update the service on the black list. Not implemented.
     *
     * @return A {@link ResponseEntity} with the service unavailable HTTP code.
     */
    @PutMapping(value = "/api/toggle/{toggle}version/{version}/service/{service}")
    public ResponseEntity<?> putToggleVersionservice() {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }


    /**
     * Delete the service from the black list. Not implemented.
     *
     * @return A {@link ResponseEntity} with the service unavailable HTTP code.
     */
    @DeleteMapping(value = "/api/toggle/{toggle}version/{version}/service/{service}")
    public ResponseEntity<?> deleteToggleVersionservice() {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Get All toggles which have a service black listed.
     *
     * @param service The service's name
     * @return a {@link ToggleServiceResponse} class instance containing all the toggles.
     */
    @GetMapping(value = "/api/service/{service}", produces = "application/json")
    public ResponseEntity<ToggleServiceResponse> getTogglesService(@PathVariable final String service) {

        final Map<String, Feature> features = ff4j.getFeatures();

        final ArrayList<FeatureRepresentation> featuresList = new ArrayList<>();

        features.forEach((s, feature) -> {
            final List<String> grantedClients = Arrays.asList(feature
                                                            .getFlippingStrategy()
                                                            .getInitParams()
                                                            .get("grantedClients")
                                                            .split("\\s*,\\s*"));
            grantedClients.forEach(client -> {
                if (service.equals(client)) {
                    featuresList.add(ImmutableFeatureRepresentation.builder()
                                         .identifier(feature.getUid())
                                         .group(feature.getGroup())
                                         .strategy(getFeatureStrategy(feature.getFlippingStrategy()))
                                         .active(feature.isEnable())
                                         .build());
                }
            });
        });

        final ToggleServiceResponse response = ImmutableToggleServiceResponse.builder()
            .uuid(UUID.randomUUID().toString())
            .featureRepresentation(featuresList.toArray(new FeatureRepresentation[featuresList.size()]))
            .service("")
            .timestamp(Instant.now().getEpochSecond())
            .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Method that converts a {@link FlippingStrategy} class instance to a {@link String}.
     *
     * @param flippingStrategy a {@link FlippingStrategy} class instance
     * @return A {@link String} featuring the flipping strategy
     */
    private String getFeatureStrategy(final FlippingStrategy flippingStrategy) {
        if (flippingStrategy == null) {
            return null;
        } else {
            return flippingStrategy.toString();
        }
    }
}