package com.mgreis.delivery.jsonmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * A class that represents the JSON response to a request to check the toggle's activity status.
 *
 * @author Mario Pereira
 * @since 1.0.0
 */
@JsonSerialize(
    as = ImmutableCheckServiceResponse.class
)
@JsonDeserialize(
    as = ImmutableCheckServiceResponse.class
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("check_response")
@Value.Immutable
public abstract class CheckServiceResponse extends AbstractResponse {

    /**
     * A method that returns the toggle's identifier (toggle:version).
     *
     * @return a {@link String} class instance containing the identifier.
     */
    @JsonProperty("identifier")
    public abstract String getIdentifier();

    /**
     * A method that returns the toggle's status.
     *
     * @return a {@link Boolean} class instance containing activity status.
     */
    @JsonProperty("active")
    public abstract Boolean getActive();
}
