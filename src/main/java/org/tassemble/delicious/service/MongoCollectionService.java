package org.tassemble.delicious.service;


public interface MongoCollectionService {
    
    
    public boolean getInDishCollection(Object objectToGet);
    public boolean saveInDishCollection(Object objectToStore);
    public boolean updateInDishCollection(Object objectToUpdate);
    public boolean deleteInDishCollection(Object objectToDelele);
    
    
    
    
    public boolean getInBannerCollection(Object objectToGet);
    public boolean saveInBannerCollection(Object objectToStore);
    public boolean updateInBannerCollection(Object objectToUpdate);
    public boolean deleteInBannerCollection(Object objectToDelele);
    
    
    
    
    
    public boolean getInThemeCollection(Object objectToGet);
    public boolean saveInThemeCollection(Object objectToStore);
    public boolean updateInThemeCollection(Object objectToUpdate);
    public boolean deleteInThemeCollection(Object objectToDelele);
    
    
    
    
    
    public boolean getInCommentCollection(Object objectToGet);
    public boolean saveInCommentCollection(Object objectToStore);
    public boolean updateInCommentCollection(Object objectToUpdate);
    public boolean deleteInCommentCollection(Object objectToDelele);
    
    

    
    
    public boolean getInActivityCollection(Object objectToGet);
    public boolean saveInActivityCollection(Object objectToStore);
    public boolean updateInActivityCollection(Object objectToUpdate);
    public boolean deleteInActivityCollection(Object objectToDelele);
    
    
    
    
    
    
     

    
    
    public boolean getInCouponCollection(Object objectToGet);
    public boolean saveInCouponCollection(Object objectToStore);
    public boolean updateInCouponCollection(Object objectToUpdate);
    public boolean deleteInCouponCollection(Object objectToDelele);
    
    
    
    
    
    
}
