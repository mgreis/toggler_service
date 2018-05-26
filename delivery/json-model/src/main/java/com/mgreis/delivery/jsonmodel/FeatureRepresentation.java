package com.mgreis.delivery.jsonmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mgreis.delivery.annotations.Nullable;
import org.immutables.value.Value;

/**
 * The JSON response class object to be marshalled and unmarshalled.
 *
 * @author Mario Pereira
 * @since 1.0.0
 */
@JsonSerialize(
    as = ImmutableFeatureRepresentation.class
)
@JsonDeserialize(
    as = ImmutableFeatureRepresentation.class
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("feature_representation")
@Value.Immutable
public abstract class FeatureRepresentation {

    /**
     * A method that returns the toggle's identifier (toggle:version).
     *
     * @return a {@link String} class instance containing the identifier.
     */
    @JsonProperty("identifier")
    public abstract String getIdentifier();

    /**
     * A method that return's the toggle's group.
     *
     * @return a {@link String} class instance containing the group.
     */
    @Nullable
    @JsonProperty("group")
    public abstract String getGroup();

    /**
     * A method that return's the toggle's strategy.
     *
     * @return a {@link String} class instance containing strategy.
     */
    @Nullable
    @JsonProperty("strategy")
    public abstract String getStrategy();

    /**
     * A method that returns the toggle's status.
     *
     * @return a {@link Boolean} class instance containing activity status.
     */
    @Nullable
    @JsonProperty("active")
    public abstract Boolean getActive();
}
