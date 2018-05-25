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
 * The JSON response class object to be marshalled and unmarshalled.
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
public abstract class ToggleServiceResponse {

    @Nullable
    @JsonProperty("service")
    public abstract String getService ();

    @JsonProperty("timestamp")
    public abstract long getTimestamp ();

    @JsonProperty("uuid")
    public abstract String getUuid ();

    @JsonProperty("feature")
    public abstract FeatureResponse [] getFeature ();
}