package com.thanhhai.demo.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@PropertySource(value = {"classpath:application.properties"})
public class DomainUtils {

    @Value("${app.domain}")
    private String appDomain;

    /**
     * Extract the sub-domain from host
     * @param host
     * @return
     */
    public String getSubDomainFromHost(@NotNull String host) {
        //InternetDomainName topPrivateDomain = InternetDomainName.from(host).topPrivateDomain();
        //String domain = InternetDomainName.from(host).topPrivateDomain().toString();
        String domain = appDomain;
        String subDomain = host.replaceAll(domain, "");
        if (subDomain.endsWith(".")) {
            subDomain = subDomain.substring(0, subDomain.length() - 1);
        }
        System.out.println("domain: " + domain + " sub: " + subDomain);
        return subDomain;
    }
}
