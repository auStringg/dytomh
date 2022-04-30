package org.qiujf.pulsoid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.qiujf.pulsoid.PulsoidBase;
import org.qiujf.pulsoid.service.PulsoidBaseService;
import org.qiujf.pulsoid.mapper.PulsoidBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author dytom
* @description 针对表【pulsoid_base】的数据库操作Service实现
* @createDate 2022-04-30 23:54:26
*/
@Service
public class PulsoidBaseServiceImpl extends ServiceImpl<PulsoidBaseMapper, PulsoidBase>
    implements PulsoidBaseService{


    @Override
    public PulsoidBase getMaxPulsoid() {
        return this.getBaseMapper().getMaxPulsoid();
    }
}




