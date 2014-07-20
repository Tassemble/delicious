package org.tassemble.delicious.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tassemble.base.commons.service.MongoDBBaseService;
import org.tassemble.delicious.config.AppConfig;
import org.tassemble.delicious.dto.DeliciousResponseDto;
import org.tassemble.utils.GsonUtils;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


@Controller
public class AllInOneController {

    
    @Autowired
    AppConfig appConfig;
    
    
    @Autowired
    MongoDBBaseService  MongoDBBaseService; 

    @Autowired
    MongoClient mongoClient;
    
    
    @RequestMapping(method=RequestMethod.GET, value="/query/{Collection}.htm")
    public void getAllByCollection(HttpServletRequest request, HttpServletResponse response, @PathVariable("Collection") String collection) throws Exception {
        
        if (!appConfig.getDebug()) {
            response.getOutputStream().write("not supported".getBytes("UTF-8"));
            return;
        }
        
        if (StringUtils.isBlank(collection)) {
            response.getOutputStream().write("collection must not be null".getBytes("UTF-8"));
            return;
        }
        
        DBCollection dbCollection = mongoClient.getDB("delicious").getCollection(collection);
        
        
        Iterator<DBObject> objectItor =  dbCollection.find().iterator();
        
        
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        for (; objectItor.hasNext(); ) {
            
            DBObject item = objectItor.next();
            Object id = item.get("_id");
            
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.putAll(item.toMap());
            resultMap.put("id", String.valueOf(id));
            resultMap.remove("_id");
            list.add(resultMap);
        }
        Map map = DeliciousResponseDto.returnDirectly(collection, list);
        
        response.getOutputStream().write(GsonUtils.toJson(map).getBytes("UTF-8"));
        return;
    }
}
