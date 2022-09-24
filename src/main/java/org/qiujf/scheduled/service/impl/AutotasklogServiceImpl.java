package org.qiujf.scheduled.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.qiujf.scheduled.entity.Autotasklog;
import org.qiujf.scheduled.service.AutotasklogService;
import org.qiujf.scheduled.mapper.AutotasklogMapper;
import org.springframework.stereotype.Service;

/**
* @author dytomh
* @description 针对表【autoTaskLog】的数据库操作Service实现
* @createDate 2022-09-24 13:05:44
*/
@Service
public class AutotasklogServiceImpl extends ServiceImpl<AutotasklogMapper, Autotasklog>
implements AutotasklogService{

}
