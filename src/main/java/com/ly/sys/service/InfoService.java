package  com.ly.sys.service;

import com.ly.sys.vo.Info;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import java.util.List;


@IocBean(fields = { "dao" })
public class InfoService extends IdEntityService<Info> {
}


