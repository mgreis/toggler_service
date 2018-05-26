package com.mgreis.delivery.jsonmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mgreis.delivery.annotations.Nullable;
import org.immutables.value.Value;
import org.immutables.value.internal.$processor$.meta.$JacksonMirrors;

/**
 *  class that represents the JSON response to a request to list toggles.
 *
 * @author Mario Pereira
 * @since 1.0.0
 */
@JsonSerialize(
    as = ImmutableToggleServiceResponse.class
)
@JsonDeserialize(
    as = ImmutableToggleServiceResponse.class
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("json_response")
@Value.Immutable
public abstract class ToggleServiceResponse extends AbstractResponse{
    /**
     * A method that returns an array of {@link FeatureRepresentation} instances.
     *
     * @return an array of {@link FeatureRepresentation} instances
     */
    @JsonProperty("feature")
    public abstract FeatureRepresentation [] getFeatureRepresentation ();
}
