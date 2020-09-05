package zhd.oa.middleware.service;

import zhd.oa.middleware.mapper.LogisticsOutSbillMapper;
import zhd.oa.middleware.model.LogisticsOutSbill;

import java.util.ArrayList;
import java.util.List;

public class LogisticsOutSbillService extends BaseService{

    private LogisticsOutSbillMapper logisticsOutSbillMapper ;

    public List<LogisticsOutSbill> getListLogisticsOutSbill(String sbillCodes){

        List<LogisticsOutSbill> listLogisticsOutSbill = new ArrayList<>();
        try{
            session = openSession();
            logisticsOutSbillMapper = session.getMapper(LogisticsOutSbillMapper.class);
            listLogisticsOutSbill = logisticsOutSbillMapper.getLogisticsOutSbill(sbillCodes);
            log.info(listLogisticsOutSbill.size()+listLogisticsOutSbill.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeSession();
        }
        return listLogisticsOutSbill;
    }



}
