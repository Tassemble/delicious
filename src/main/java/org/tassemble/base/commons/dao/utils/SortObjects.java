package org.tassemble.base.commons.dao.utils;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tassemble.base.commons.dto.ObjectConfig;


public class SortObjects {

    public static <DomainObject> List<DomainObject> sort(Long[] ids, List<DomainObject> objects, ObjectConfig config) {
        List ret = new ArrayList();
        Long[] arr$ = ids;
        int len$ = arr$.length;
        long id;
        Iterator iter;
        for (int i$ = 0; i$ < len$; ++i$) {
            id = arr$[i$].longValue();
            for (iter = objects.iterator(); iter.hasNext();) {
                Object obj = iter.next();
                if (id == config.getId(obj)) {
                    ret.add(obj);
                    break;
                }
            }
        }
        return ret;
    }
}
