package org.tassemble.delicious.dto;

import java.util.HashMap;
import java.util.Map;


public class DeliciousResponseDto {
    
    
    Map<String, Object> result = new HashMap<String, Object>();
    
    
    
    public DeliciousResponseDto() {
        result.put("code", 0);
    }
    
    
    
    
    public void putObject(String key, Object value) {
        result.put(key, value);
    }
    
    
    
    public static Map<String, Object> returnDirectly(String key, Object value) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 0);
        result.put(key, value);
        return result;
    }
    
    
}
