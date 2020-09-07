package zhd.oa.middleware.service;

import zhd.oa.middleware.mapper.LogisticsSbillInfoMapper;
import zhd.oa.middleware.model.LogisticsSbillInfo;

import java.util.ArrayList;
import java.util.List;

public class LogisticsSbillInfoService extends BaseService{

    private LogisticsSbillInfoMapper logisticsSbillInfoMapper ;

    public List<LogisticsSbillInfo> getListLogisticsSbillInfo(String sbillCodes){

        List<LogisticsSbillInfo> listLogisticsSbillInfo = new ArrayList<>();

        try{
            session = openSession();
            logisticsSbillInfoMapper = session.getMapper(LogisticsSbillInfoMapper.class);
            listLogisticsSbillInfo = logisticsSbillInfoMapper.getSbillInfoList(sbillCodes);
            log.info(listLogisticsSbillInfo.size()+listLogisticsSbillInfo.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeSession();
        }
        return listLogisticsSbillInfo;


    }


}
