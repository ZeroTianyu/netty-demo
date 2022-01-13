package cn.itcast.netty.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @Author: guotianyu
 * @Description:
 * @Date: 2021/10/18
 **/
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();

        channel.connect(new InetSocketAddress("localhost",8080));

        SocketAddress address = channel.getLocalAddress();

        System.out.println("waiting ....");

    }
}
