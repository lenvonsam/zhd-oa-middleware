package zhd.oa.middleware.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;

public class ExceptionController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(ExceptionController.class);

	public static void handle(Exception e, Request req, Response resp) {
		log.error("exception handle error", e);
		// TODO 根据错误返回error
		resp.body("can find page");
	}

	@Override
	public void router() {
		// TODO Auto-generated method stub
	}
}
