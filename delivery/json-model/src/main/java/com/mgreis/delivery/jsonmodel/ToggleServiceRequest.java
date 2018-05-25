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
    as = ImmutableToggleServiceRequest.class
)
@JsonDeserialize(
    as = ImmutableToggleServiceRequest.class
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("json_request")
@Value.Immutable
public abstract class ToggleServiceRequest {

    @JsonProperty("toggle")
    public abstract String getToggle ();

    @JsonProperty("version")
    public abstract String getVersion ();

    @Nullable
    @JsonProperty("active")
    public abstract Boolean getActive ();

    @Nullable
    @JsonProperty("description")
    public abstract String getDescription ();

    @Nullable
    @JsonProperty("group")
    public abstract String getFeature();

    @Nullable
    @JsonProperty("blacklist")
    public abstract String getBlacklist();
}