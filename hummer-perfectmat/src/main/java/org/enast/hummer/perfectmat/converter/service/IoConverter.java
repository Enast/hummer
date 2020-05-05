package org.enast.hummer.perfectmat.converter.service;

import org.enast.hummer.perfectmat.entity.IOList;

import java.util.List;
import java.util.Map;

/**
 * @author zhujinming6
 * @create 2019-11-21 15:13
 * @update 2019-11-21 15:13
 **/
public interface IoConverter {

    void analysis(List<IOList> list, Map<String, Object> status);
}
