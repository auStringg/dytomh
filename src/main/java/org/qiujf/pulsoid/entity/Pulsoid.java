package org.qiujf.pulsoid.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@lombok.NoArgsConstructor
@lombok.Data
public class Pulsoid {


    @JsonProperty("measured_at")
    private Long measuredAt;
    @JsonProperty("data")
    private DataDTO data;

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class DataDTO {
        @JsonProperty("heart_rate")
        private Integer heartRate;
    }

    /**
     * 获取心跳
     * @return
     */
    public  Integer getHeartRate(){
        return data.getHeartRate();
    }

    /**
     * 获取当前日期
     * @return
     */
    public LocalDateTime getTime(){
        return LocalDateTime.now();
    }
}
