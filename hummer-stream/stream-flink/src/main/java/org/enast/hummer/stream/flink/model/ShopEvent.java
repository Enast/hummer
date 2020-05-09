package org.enast.hummer.stream.flink.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopEvent {

    /**
     * Shop Id
     */
    private Long id;

    /**
     * Shop name
     */
    private String name;

    /**
     * shop owner Id
     */
    private Long ownerId;

    /**
     * shop owner name
     */
    private String ownerName;

    /**
     * shop status: (1:正常, -1:关闭, -2:冻结)
     */
    private int status;

    /**
     * shop type: (1:门店 2:商家 3:出版社)
     */
    private int type;

    /**
     * shop phone
     */
    private String phone;

    /**
     * shop email
     */
    private String email;

    /**
     * shop address
     */
    private String address;

    /**
     * shop image url
     */
    private String imageUrl;
}
