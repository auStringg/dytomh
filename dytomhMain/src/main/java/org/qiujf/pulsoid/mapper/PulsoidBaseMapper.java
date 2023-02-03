package org.qiujf.pulsoid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.qiujf.pulsoid.PulsoidBase;

/**
* @author dytom
* @description 针对表【pulsoid_base】的数据库操作Mapper
* @createDate 2022-04-30 23:54:26
* @Entity org.qiujf.pulsoid.PulsoidBase
*/
public interface PulsoidBaseMapper extends BaseMapper<PulsoidBase> {



    PulsoidBase getMaxPulsoid();
}




