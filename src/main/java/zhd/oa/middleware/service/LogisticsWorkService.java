package zhd.oa.middleware.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import zhd.oa.middleware.mapper.LogisticsWorkMapper;
import zhd.oa.middleware.utils.WorkflowUtil;

import java.util.HashMap;
import java.util.Map;

public class LogisticsWorkService extends BaseService {

	private LogisticsWorkMapper logisticsWorkMapper;

	/**
	 * 提交流程
	 * 
	 * @param jsonObject
	 * @return
	 */
	public Map subLogisticsWork(JSONObject jsonObject) {

		Map resultMap = checkLogisticsData(jsonObject);
		try {
			if ("0".equals(resultMap.get("success"))) {
				session = openSession();
				logisticsWorkMapper = session.getMapper(LogisticsWorkMapper.class);
				String requestid = logisticsWorkMapper.getRequestidBySourceid(resultMap.get("goodSourceNo").toString());
				String submitResult = WorkflowUtil.shareInstance().operateRequest(Integer.parseInt(requestid), 104,
						"submit", "触发系统提交");
				log.info("submitResult===>" + submitResult);
				if ("failed".equals(submitResult)) {
					resultMap.put("success", "1");
					resultMap.put("msg", "流程提交失败!流程已被删或是已归档!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return resultMap;
	}

	/**
	 * 校验数据
	 * 
	 * @param jsonObject
	 * @return
	 */
	public Map checkLogisticsData(JSONObject jsonObject) {

		Map resultMap = new HashMap();
		String success = "1";// 0:成功 1:失败
		String msg = ""; // 主数据消息
		String msgDt = ""; // 明细数据消息
		String goodSourceNo = "";

		try {

			session = openSession();
			logisticsWorkMapper = session.getMapper(LogisticsWorkMapper.class);

			goodSourceNo = jsonObject.getString("goodSourceNo");
			if (null == goodSourceNo || "".equals(goodSourceNo)) {
				msg += "未接收到货源单号!";
			}
			String mainid = logisticsWorkMapper.getMainid(goodSourceNo);
			log.info("mainid===>" + mainid);
			if (null == mainid || "".equals(mainid)) {
				msg += "未找到对应流程!";
			}
			String status = logisticsWorkMapper.checkWorkStatus(logisticsWorkMapper.getRequestid(mainid));
			if (null == status || "3".equals(status)) {
				msg += "流程已归档,或是已被删除!";
			}

			JSONArray detailJSONArray = jsonObject.getJSONArray("details");
			if (null == detailJSONArray) {
				msg += "参数异常!";
			}

			if ("".equals(msg)) {
				for (int i = 0; i < detailJSONArray.size(); i++) {
					msgDt = "";
					JSONObject detailJSONObject = (JSONObject) detailJSONArray.get(i);

					String closeCom = detailJSONObject.getString("settleCompany");// 0:交投智联云 1:线下结算
					String tranCom = detailJSONObject.getString("transportCompany"); // 运输单位名称
					String driver = detailJSONObject.getString("driverName"); // 司机姓名
					String phone = detailJSONObject.getString("driverPhone");// 手机号
					String carNo = detailJSONObject.getString("transportCarNo");// 车牌号
					String freightMode = detailJSONObject.getString("freightType");// 0:不含税 1:含税
					String price = detailJSONObject.getString("freightPrice");// 两位小数
					String unit = detailJSONObject.getString("freightUnit");// 0:/车 1:/吨
					String weight = detailJSONObject.getString("transportWeight");// 三位小数
					String money = detailJSONObject.getString("freightAmount");// 两位小数
					String carMax = detailJSONObject.getString("carMax");//
					String carLength = detailJSONObject.getString("carLength");//
					String remk = detailJSONObject.getString("remark");//

					String comCode = logisticsWorkMapper.checkComname(tranCom);
//                    String driverCode = logisticsWorkMapper.checkDriver(driver);
					String carCode = "";
					Boolean car = carNo.matches(
							"^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Za-z](([0-9]{5}[DFdf])|([DFdf]([A-Ha-hJ-Nj-nP-Zp-z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Za-z][A-Ha-hJ-Nj-nP-Zp-z0-9]{4}[A-Ha-hJ-Nj-nP-Zp-z0-9挂学警港澳使领]))$");
//                    if(car){
//                        carCode = logisticsWorkMapper.checkCarNo(carNo);
//                        if(null==carCode || "".equals(carCode)){
//                            msgDt += "Erp系统未收录该车牌号!";
//                        }
//                    }

					if (null == comCode || (!"0".equals(closeCom) && !"1".equals(closeCom))) {
						msgDt += "结算单位不正确!";
					}
					if (null == comCode || "".equals(comCode)) {
						msgDt += "运输单位不在系统中!";
					}
//                    if(null==driverCode || "".equals(driverCode)){
//                        msgDt += "司机不在系统中!";
//                    }
//                    if(null== driver || driver.length() > 200 ){
//                        msgDt += "司机长度不要超过200字符!";
//                    }
					if (null != phone && !phone.matches("^1[3-9]\\d{9}$")) {
						msgDt += "手机号不正确!";
					}
					if (null == carNo || !car) {
						msgDt += "车牌号不正确!";
					}
					if (null == freightMode || (!"0".equals(freightMode) && !"1".equals(freightMode))) {
						msgDt += "运费类型不正确!";
					}
					if (null == price || !price.matches(
							"^(([1-9][0-9]{0,14})|([0]{1})|(([0]\\.\\d{1,2}|[1-9][0-9]{0,14}\\.\\d{1,2})))$")) {
						msgDt += "单价格式不正确!";
					}
					if (null == unit || !"0".equals(unit) && !"1".equals(unit)) {
						msgDt += "运费单位不正确!";
					}
					if (null == weight || !weight.matches("^[0-9]+\\.{0,1}[0-9]{0,3}$")) {
						msgDt += "吨位格式不正确!";
					}
					if (null == money || !money.matches(
							"^(([1-9][0-9]{0,14})|([0]{1})|(([0]\\.\\d{1,2}|[1-9][0-9]{0,14}\\.\\d{1,2})))$")) {
						msgDt += "金额格式不正确!";
					}
					if (null != carMax && carMax.length() >= 20) {
						msgDt += "载荷量不要超过20个字符!";
					}
					if (null != carLength && carLength.length() >= 20) {
						msgDt += "车长不要超过20个字符!";
					}
					if (null != remk && remk.length() >= 500) {
						msgDt += "备注不要超过500个字符";
					}
					if (!msgDt.equals("")) {
						msg += "第" + (i + 1) + "条数据：" + msgDt;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}
		if ("".equals(msg)) {
			insetLogisticsWorkDetail(jsonObject);
			success = "0";
			msg = "执行成功!";
		}

		resultMap.put("success", success);
		resultMap.put("msg", msg);
		resultMap.put("goodSourceNo", goodSourceNo);

		return resultMap;
	}

	/**
	 * 校验结束后再去进行插入操作
	 * 
	 * @param jsonObject
	 */
	public void insetLogisticsWorkDetail(JSONObject jsonObject) {

		try {

			session = openSession();
			logisticsWorkMapper = session.getMapper(LogisticsWorkMapper.class);
			String goodSourceNo = jsonObject.getString("goodSourceNo");
			String mainid = logisticsWorkMapper.getMainid(goodSourceNo);

			JSONArray detailJSONArray = jsonObject.getJSONArray("details");
			// 插入前批量删除原同步明细数据
			logisticsWorkMapper.batchDeleteByMainId(mainid);

			for (int i = 0; i < detailJSONArray.size(); i++) {

				JSONObject detailJSONObject = (JSONObject) detailJSONArray.get(i);

				String closeCom = detailJSONObject.getString("settleCompany");// 0:交投智联云 1:线下结算
				String tranCom = detailJSONObject.getString("transportCompany"); // 运输单位名称
				String driver = detailJSONObject.getString("driverName"); // 司机姓名
				String phone = detailJSONObject.getString("driverPhone");// 手机号
				String carNo = detailJSONObject.getString("transportCarNo");// 车牌号
				String freightMode = detailJSONObject.getString("freightType");// 0:不含税 1:含税
				String price = detailJSONObject.getString("freightPrice");// 两位小数
				String unit = detailJSONObject.getString("freightUnit");// 0:/车 1:/吨
				String weight = detailJSONObject.getString("transportWeight");// 三位小数
				String money = detailJSONObject.getString("freightAmount");// 两位小数
				String carMax = detailJSONObject.getString("carMax");//
				String carLength = detailJSONObject.getString("carLength");//
				String remk = detailJSONObject.getString("remark");//

				carMax = null == carMax ? "" : carMax;
				carLength = null == carLength ? "" : carLength;
				remk = null == remk ? "" : remk;
				driver = null == driver ? "" : driver;

				String comCode = logisticsWorkMapper.checkComname(tranCom);
//                String driverCode = logisticsWorkMapper.checkDriver(driver);
//                String carCode = logisticsWorkMapper.checkCarNo(carNo);
				boolean result = logisticsWorkMapper.insetLogisticsWorkDetail(mainid, closeCom, comCode, driver, phone,
						carNo, freightMode, price, unit, weight, money, carMax, carLength, "【物流平台配车】" + remk);
				log.info(detailJSONObject.toJSONString() + "执行结果--->" + result);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}
	}

	/**
	 * 记录接口调用
	 * 
	 * @param data   传的参数
	 * @param result 操作结果
	 * @param remk   备注
	 * @param url    地址
	 */
	public void recordLogisticsRequest(String requestid, String data, String result, String remk, String url) {

		try {
			session = openSession();
			logisticsWorkMapper = session.getMapper(LogisticsWorkMapper.class);
			logisticsWorkMapper.insertLogisticsRecord(requestid, data, result, remk, url);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}

	}

}
