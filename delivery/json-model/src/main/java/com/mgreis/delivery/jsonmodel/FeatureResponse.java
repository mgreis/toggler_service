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
    as = ImmutableFeatureResponse.class
)
@JsonDeserialize(
    as = ImmutableFeatureResponse.class
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("feature_response")
@Value.Immutable
public abstract class FeatureResponse {

    @JsonProperty("identifier")
    public abstract String getIdentifier ();

    @Nullable
    @JsonProperty("group")
    public abstract String getGroup ();

    @Nullable
    @JsonProperty("strategy")
    public abstract String getStrategy ();

    @Nullable
    @JsonProperty("active")
    public abstract Boolean getActive ();
}
