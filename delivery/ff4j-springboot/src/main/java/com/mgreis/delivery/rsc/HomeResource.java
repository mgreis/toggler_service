package com.mgreis.delivery.rsc;

import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

    @Autowired
    private FF4j ff4j;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html")
    public String sayHello() {
        StringBuilder response = new StringBuilder("<html><body><ul>");
        response.append(
            "<li> To access the <b>WebConsole</b> please go to <a href=\"./ff4j-web-console/home\">ff4j-web-console</a>");
        response.append("<li> To access the <b>REST API</b> please go to <a href=\"./api/ff4j\">api/ff4j</a>");
        response.append(
            "<li> To access the <b>Swagger File </b> please go to <a href=\"./v2/api-docs\">/v2/api-docs</a>");
        response.append(
            "<li> To access the <b>Swagger -ZUI </b> please go to <a href=\"./swagger-ui.html\">/swagger-ui.html</a></ul>");

        response.append(
            "<p>Is <span style=\"color:red\">Awesome</span> feature activated ? from  ff4j.check(\"AwesomeFeature\") <span style=\"color:blue\">");
        response.append(ff4j.check("AwesomeFeature"));
        response.append("</span></body></html>");

        return response.toString();
    }
}
