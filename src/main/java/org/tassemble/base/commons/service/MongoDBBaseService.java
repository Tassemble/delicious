package org.tassemble.base.commons.service;


public interface MongoDBBaseService {
    
    public Object getInCollection(Object objectToGet);
    public boolean saveInCollection(Object objectToStore);
    public boolean updateInCollection(Object objectToUpdate);
    public boolean deleteInCollection(Object objectToDelele);
    
    
    
}
