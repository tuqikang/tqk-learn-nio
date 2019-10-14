package cn.tqktqk.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * █████▒█      ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 *
 * @author ：涂齐康
 * @date ：Created in 2019/10/13 1:20 下午
 * @description：
 * @modified By：
 * @version:
 */
public class NioTestEight {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("OutTest");
        FileInputStream fis = new FileInputStream("InTest");

        //直接缓冲buffer c/c++ malloc函数 buffer有个成员变量long address专门给dirtybuffer使用，指向堆外内存的地址，能直接找到数据。少了一次数据拷贝的过程，zero copy
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
        //ByteBuffer byteBuffer = ByteBuffer.allocate(10);//底层new HeapByteBuffer() 位于堆，和普通对象没区别。实际多了一次数据拷贝的过程，会把java内存中的数据放在java外中，然后和io设备进行操作
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        while (true){
            byteBuffer.clear();
            int read = inChannel.read(byteBuffer);
            System.out.println("read:"+read);
            if (read==-1){
                break;
            }
            byteBuffer.flip();

            outChannel.write(byteBuffer);
        }
        inChannel.close();
        outChannel.close();
    }
}
