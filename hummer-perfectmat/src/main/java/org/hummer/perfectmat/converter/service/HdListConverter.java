package org.hummer.perfectmat.converter.service;

import org.hummer.perfectmat.HdStatus;

import java.util.List;
import java.util.Map;

/**
 * @author zhujinming6
 * @create 2019-11-21 16:10
 * @update 2019-11-21 16:10
 **/
public interface HdListConverter {
    void analysis(List<HdStatus> baseInfoList, Map<String, Object> status);
}
