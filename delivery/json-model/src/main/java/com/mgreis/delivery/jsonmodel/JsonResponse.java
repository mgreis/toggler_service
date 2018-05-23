package com.mgreis.delivery.jsonmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * The JSON response class object to be marshalled and unmarshalled.
 *
 * @author Mario Pereira
 * @since 1.0.0-SNAPSHOT
 */
@JsonSerialize(
    as = ImmutableJsonResponse.class
)
@JsonDeserialize(
    as = ImmutableJsonResponse.class
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("json_response")
@Value.Immutable
public abstract class JsonResponse {

    @JsonProperty("version")
    public abstract String getVersion ();

    @JsonProperty("feature")
    public abstract String getFeature ();
}
