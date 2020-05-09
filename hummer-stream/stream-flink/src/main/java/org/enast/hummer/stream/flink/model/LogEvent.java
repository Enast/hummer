package org.enast.hummer.stream.flink.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogEvent {
    //the type of log(app、docker、...)
    private String type;

    // the timestamp of log
    private Long timestamp;

    //the level of log(debug/info/warn/error)
    private String level;

    //the message of log
    private String message;

    //the tag of log(appId、dockerId、machine hostIp、machine clusterName、...)
    private Map<String, String> tags = new HashMap<>();
}