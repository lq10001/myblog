package  com.ly.blog.service;

import com.ly.blog.vo.Blog;
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
public class BlogService extends IdEntityService<Blog> {

	public static String CACHE_NAME = "blog";
    public static String CACHE_COUNT_KEY = "blog_count";

    public List<Blog> queryCache(Condition c,Page p)
    {
        List<Blog> list_blog = null;
        String cacheKey = "blog_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_blog = this.query(c, p);
            cache.put(new Element(cacheKey, list_blog));
        }else{
            list_blog = (List<Blog>)cache.get(cacheKey).getObjectValue();
        }
        return list_blog;
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


