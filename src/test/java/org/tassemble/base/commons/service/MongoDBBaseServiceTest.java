package org.tassemble.base.commons.service;

import java.util.HashMap;
import java.util.Map;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tassemble.base.dao.BaseTestCase;
import org.tassemble.delicious.domain.Friend;
import org.tassemble.delicious.domain.Hobby;
import org.tassemble.utils.GsonUtils;

import com.mongodb.DB;
import com.mongodb.MongoClient;


public class MongoDBBaseServiceTest extends BaseTestCase{
    
    

    @Autowired
    MongoClient mongoClient;
    
    @Test
    public void testORM() {
        DB db = mongoClient.getDB("dbname");

        Jongo jongo = new Jongo(db);
        MongoCollection friends = jongo.getCollection("friends");
        Friend newFriend = new Friend();
        newFriend.setName("more");
        
        Hobby hobby = new Hobby();
        hobby.setName("play basketball");
        newFriend.setHobby(hobby);

        
        Map<String, String> map = new HashMap<String, String>();
        map.put("age", "18");
        newFriend.setBasicInfos(map);
        
        
        
        friends.insert(newFriend);
        
        
        Friend one = friends.findOne("{name: 'more'}").as(Friend.class);
        System.out.println(GsonUtils.toJson(one));
    }
    

}
