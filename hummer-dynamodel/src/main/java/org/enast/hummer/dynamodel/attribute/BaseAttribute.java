package org.enast.hummer.dynamodel.attribute;

import org.enast.hummer.dynamodel.conmon.DataType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhujinming6
 * @create 2020-04-11 16:33
 * @update 2020-04-11 16:33
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseAttribute {

    private String identify;
    private String name;
    private DataType dataType;
    private Boolean required;
    private Boolean unique;
    private String accessMode;
    private Integer dataLength;
    private Boolean notNull;
    private String defaultValue;
}
