package cn.itcast.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: guotianyu
 * @Description:
 * @Date: 2021/10/13
 **/
@Slf4j
public class TestByteBuffer {

    public static void main(String[] args) {
        // FileChannel
        // 1. 输入输出流     2.RandomAccessFile

        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            // 准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);

            while (true) {
                // 从 channel 读取数据,向 buffer 写入
                int len = channel.read(buffer);
                log.debug("读取到的字节数为：{}", len);
                if (len == -1) {   // 等于 -1 时，表示没有内容了
                    break;
                }
                // 打印 buffer 的内容
                buffer.flip(); //切换到读模式
                while (buffer.hasRemaining()) { //是否还有剩余未读的数据
                    byte b = buffer.get();
                    log.debug("实际字节:{}", b);
                }

                buffer.clear();//读完一次之后, buffer 切换至写模式
            }
        } catch (IOException e) {


        }
    }
}
