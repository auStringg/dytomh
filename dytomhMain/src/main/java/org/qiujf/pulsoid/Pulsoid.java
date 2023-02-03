package org.qiujf.pulsoid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Pulsoid {


    @JsonProperty("measured_at")
    private Long measuredAt;
    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("heart_rate")
        private Integer heartRate;
    }
    public Integer getHearRate(){
        return data.heartRate;
    }
}
