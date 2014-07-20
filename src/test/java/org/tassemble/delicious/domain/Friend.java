package org.tassemble.delicious.domain;

import java.util.HashMap;
import java.util.Map;


public class Friend {
    String name;

    
    Hobby hobby;
    
    
    Map<String, String> basicInfos = new HashMap<String, String>();
    
    
    
    
    
    public Map<String, String> getBasicInfos() {
        return basicInfos;
    }



    
    public void setBasicInfos(Map<String, String> basicInfos) {
        this.basicInfos = basicInfos;
    }



    public Hobby getHobby() {
        return hobby;
    }


    
    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }


    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return "Friend [name=" + name + ", hobby=" + hobby + "]";
    }
    
    
    
}
