package org.enast.hummer.cluster.raft;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.channels.AsynchronousSocketChannel;

/**
 * @author zhujinming6
 * @create 2020-05-08 18:58
 * @update 2020-05-08 18:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIOSession {

    private AsynchronousSocketChannel channel;

}
