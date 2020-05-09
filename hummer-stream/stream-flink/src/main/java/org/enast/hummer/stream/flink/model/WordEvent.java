package org.enast.hummer.stream.flink.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WordEvent {
    private String word;
    private int count;
    private long timestamp;
}
