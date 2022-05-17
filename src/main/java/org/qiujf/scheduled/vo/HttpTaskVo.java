package org.qiujf.scheduled.vo;


import lombok.Data;
import org.apache.http.Header;

import java.util.List;
import java.util.Map;

@Data
public class HttpTaskVo {
    private String uri;
    private List<Header> headers;
}
