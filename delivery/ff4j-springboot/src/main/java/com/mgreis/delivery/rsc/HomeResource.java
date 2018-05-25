package com.mgreis.delivery.rsc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Home resource ReST controller class.
 *
 * @author Mario Pereira
 * @since 1.0.0
 */
@RestController
public class HomeResource {

    /**
     * The home ReST controller class method.
     *
     * @return a {@link String} containing the web page.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html")
    public String homeRSC() {
        final String response = "<html><body><ul>" +
            "<li> To access the <b>WebConsole</b> please go to <a href=\"./ff4j-web-console/home\">ff4j-web-console</a>" +
            "<li> To access the <b>REST API</b> please go to <a href=\"./api/ff4j\">api/ff4j</a>" +
            "<li> To access the <b>Swagger File </b> please go to <a href=\"./v2/api-docs\">/v2/api-docs</a>" +
            "<li> To access the <b>Swagger -ZUI </b> please go to <a href=\"./swagger-ui.html\">/swagger-ui.html</a></ul>";
        return response;
    }
}
