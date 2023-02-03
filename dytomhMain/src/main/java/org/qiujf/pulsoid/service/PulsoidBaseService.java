package org.qiujf.pulsoid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.qiujf.pulsoid.PulsoidBase;

/**
* @author dytom
* @description 针对表【pulsoid_base】的数据库操作Service
* @createDate 2022-04-30 23:54:26
*/
public interface PulsoidBaseService extends IService<PulsoidBase> {

    PulsoidBase getMaxPulsoid();

}
