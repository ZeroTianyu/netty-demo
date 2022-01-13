package cn.itcast.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Author: guotianyu
 * @Description:
 * @Date: 2021/10/18
 **/
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        // 1.创建 selector,管理多个 channel
        Selector selector = Selector.open();

        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);

        // 2.建立 selector 和 channel的联系（注册）
        // SelectionKey 就是事件发生后，通过它,可以知道事件和哪个channel的事件
        SelectionKey sscKey = socketChannel.register(selector, 0, null);

        // key 只关注 accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key:{}",sscKey);

        socketChannel.bind(new InetSocketAddress(8080));
        while (true){
            // 3. select 方法,没有事件发生，线程阻塞,有事件，线程才会恢复执行
            selector.select();

            // 4. 处理事件 selectedKeys 内部包含了所有发生的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();// 拿到所有可读可写可连接的集合
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                log.debug(" key:{}",key);
                // 5. 区分事件类型
                if (key.isAcceptable()) {       //如果是 Accept
                    ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);

                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);

                    log.debug("ServerSocket: {}" ,sc);
                }else if(key.isReadable()){     // 如果是 read

                    SocketChannel channel = (SocketChannel)key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    channel.read(buffer);
                    buffer.flip();
//                    debugRead(buffer);

                }


                key.cancel();
            }

        }

    }
}
