package io.marketplace.services.ocr.implementation.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Base64;

@Configuration
public class LocalAutoConfiguration {

    private static class HttpHeaderFilter extends HttpServletRequestWrapper {

        public HttpHeaderFilter(HttpServletRequest request){
            super(request);
        }

        protected String buildFakeJWT() {
            String jwtHeader = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IllqYzNORFE0TnpNNE4yUmpNamxpWWpjME1qUTJOVEExTm1Oak4yRmhabVEyWkdVM05HWTFOUSJ9";
            String jwtPayload = "{\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/role\": [\n" +
                    "    \"Internal\\/everyone\",\n" +
                    "    \"Internal\\/selfsignup\"\n" +
                    "  ],\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/applicationtier\": \"Unlimited\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/mobile\": \"084972966267\",\n" +
                    "  \"iss\": \"wso2.org\\/products\\/am\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/enduserTenantId\": \"1\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/applicationUUId\": \"e7f6e5ed-4e91-49e2-8816-b9816adc123e\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/created\": \"2020-04-03T03:43:14\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/identity\\/preferredChannel\": \"SMS\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/subscriber\": \"admin\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/lastname\": \"Doe\",\n" +
                    "  \"exp\": 1586160198,\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/applicationid\": \"14\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/usertype\": \"APPLICATION_USER\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/apicontext\": \"\\/helloworld-service\\/v1.0.0\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/userprincipal\": \"2ebded4d-57c0-4874-8c59-306198517746\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/userid\": \"cf8a8cb7-17f0-4f7d-bf78-14961d1e6456\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/username\": \"2ebded4d-57c0-4874-8c59-306198517746\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/keytype\": \"PRODUCTION\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/version\": \"v1.0.0\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/location\": \"https:\\/\\/wso2apim-is.101digital.io\\/t\\/abc.com\\/scim2\\/Users\\/cf8a8cb7-17f0-4f7d-bf78-14961d1e6456\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/applicationname\": \"TUAN_APP\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/enduser\": \"2ebded4d-57c0-4874-8c59-306198517746@abc.com\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/givenname\": \"John\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/resourceType\": \"User\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/modified\": \"2020-04-03T03:44:16\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/tier\": \"Unlimited\",\n" +
                    "  \"http:\\/\\/wso2.org\\/claims\\/identity\\/accountLocked\": \"false\"\n" +
                    "}";
            String jwtSinature = "KLtOzyLz0KeXl1NQX1We66RdFs52U82o8WtFOu+u1xD8lZX6S971qUxL8apNewJ3kqmPHjDsfrmJA+5QmqflfmnhDEmI8h8gUnFBKmld2ppjyY3/k0n27zkXek0BouQUtiAOaohZcjacO/TlsweEsz2IPZLgqiFMB+b2/F7i2yU/U99E7PnS5cUy+2UEpc6XyuBsTFDVPDO9tAZP6PJIoGyjJdSbnqgU6erjT5SZEYjb69Qr56XyUbbUy2qaZjmoDyZkMfZq2jecHS1Wd96Iwb3CoXW65yQ15hPppx38PhkHnq6o2A+el7GHZroXbVvXYcqeOm7W2VBe1h55b2+r1Q==";

            String jwt = jwtHeader + "." +
                    Base64.getEncoder().encodeToString(jwtPayload.getBytes()) + "." +
                    jwtSinature;
            return jwt;
        }

        @Override
        public String getHeader(String name) {
            HttpServletRequest request = (HttpServletRequest)getRequest();
            if (name.equalsIgnoreCase("X-JWT-Assertion")) {
                return buildFakeJWT();
            } else {
                return request.getHeader(name);
            }
        }

    }
    private static class JWTDebugFilter implements Filter {

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            if(servletRequest instanceof HttpServletRequest) {
                HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
                HttpHeaderFilter request = new HttpHeaderFilter(httpServletRequest);
                filterChain.doFilter(request, servletResponse);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }

            return;
        }
    }

    @Bean
    public FilterRegistrationBean<JWTDebugFilter> jwtDebugFilter(){
        FilterRegistrationBean<JWTDebugFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new JWTDebugFilter());
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
