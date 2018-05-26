package com.mgreis.delivery.jsonmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mgreis.delivery.annotations.Nullable;
import org.immutables.value.Value;

/**
 * The JSON request class to create a new toggle.
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
    /**
     * Get the toggle name.
     * @return a {@link String} containing the toggle name.
     */
    @JsonProperty("toggle")
    public abstract String getToggle();

    /**
     * Get the version.
     * @return a {@link String} containing the version.
     */
    @JsonProperty("version")
    public abstract String getVersion();

    /**
     * Whether or not the toggle is active.
     * @return a {@link Boolean} classs instance that expresses whether or not the toggle is active.
     */
    @Nullable
    @JsonProperty("active")
    public abstract Boolean getActive();

    /**
     * Get the description.
     * @return a {@link String} containing the toggle's description.
     */
    @Nullable
    @JsonProperty("description")
    public abstract String getDescription();

    /**
     * A {@link String} class instance containing a list of services separated by commas.
     * This feature is not active  in these services, regardless of the toggle active status.
     *
     * @return  A {@link String} class instance containing the blacklist's service
     */
    @Nullable
    @JsonProperty("blacklist")
    public abstract String getBlacklist();
}