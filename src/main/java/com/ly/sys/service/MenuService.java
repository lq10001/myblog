package  com.ly.sys.service;

import com.ly.sys.vo.Menu;
import org.nutz.dao.Condition;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class MenuService extends IdEntityService<Menu> {

	public static String CACHE_NAME = "menu";
    public static String CACHE_COUNT_KEY = "menu_count";

    public List<Menu> queryCache(Condition c,Page p)
    {
        List<Menu> list_menu = null;
        String cacheKey = "menu_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_menu = this.query(c, p);
            cache.put(new Element(cacheKey, list_menu));
        }else{
            list_menu = (List<Menu>)cache.get(cacheKey).getObjectValue();
        }
        return list_menu;
    }

    public int listCount(Condition c)
    {
        Long num = 0L;
        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(CACHE_COUNT_KEY) == null)
        {
            num = Long.valueOf(this.count(c));
            cache.put(new Element(CACHE_COUNT_KEY, num));
        }else{
            num = (Long)cache.get(CACHE_COUNT_KEY).getObjectValue();
        }
        return num.intValue();
    }



}


