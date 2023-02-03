package org.qiujf.customMessageCover.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.qiujf.customMessageCover.BigDecimal2StringJsonSerializer;

import java.math.BigDecimal;

@Data
public class User {
    String name;
    int age;
    @JsonSerialize(using = BigDecimal2StringJsonSerializer.class)
    BigDecimal money;
    BigDecimal money2;
}
