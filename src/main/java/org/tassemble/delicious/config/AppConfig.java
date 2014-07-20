package org.tassemble.delicious.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author CHQ
 * @date Mar 1, 2014
 * @since 1.0
 */
public class AppConfig {
	
	
	private String domain;
	
	
	@Value("${debug}")
	private Boolean debug;
	
	

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

    
    public Boolean getDebug() {
        return debug;
    }

    
    public void setDebug(Boolean debug) {
        this.debug = debug;
    }
	
	
	

	
}
