package org.enast.hummer.perfectmat.service;


import org.enast.hummer.perfectmat.ResourceQuotas;
import org.enast.hummer.perfectmat.ResourceCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author zhujinming6
 * @create 2018-07-06 12:57
 * @update 2018-07-06 12:57
 **/
@Service
public class PropertiesServiceImpl implements PropertiesService {

    private Logger log = LoggerFactory.getLogger(PropertiesServiceImpl.class);

    @Override
    public void sendStatus(ResourceQuotas quotas, ResourceCache simpleVO) {
        // TOODs
    }

}
