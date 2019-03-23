package zhd.oa.middleware.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// FIXME 探索父类继承自动注入
public abstract class BaseController {
	protected Logger log = LoggerFactory.getLogger(BaseController.class);

	abstract public void router();
}
