package org.qiujf.customMessageCover.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.qiujf.customMessageCover.BigDecimal2StringJsonSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class User {
    String name;
    int age;
    @JsonSerialize(using = BigDecimal2StringJsonSerializer.class)
    BigDecimal money;
    BigDecimal money2;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date createTime;
}
