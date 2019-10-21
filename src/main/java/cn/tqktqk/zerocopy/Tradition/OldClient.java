package cn.tqktqk.zerocopy.Tradition;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

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
 * @date ：Created in 2019/10/21 11:41 上午
 * @description：
 * @modified By：
 * @version:
 */
public class OldClient {

    // /Users/tuqikang/Desktop/linux.jpg
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8899);
        String fileName = "/Users/tuqikang/Desktop/linux.jpg";

        //用户空间切换成内核空间(上下文切换),内核去磁盘拿到数据，通过DMA放入自己的buffer中，再copy到用户空间的buffer(再次上下文切换)
        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while ((readCount = inputStream.read(buffer)) >= 0) {
            total += readCount;
            //和上面差不多,拷到内核空间后，再拷贝到socket缓冲区中，然后socket发送到对应主机，然后done，write() returns（上下文切换）
            dataOutputStream.write(buffer);
        }

        System.out.println("发送总字节数:" + total + "，耗时:" + (System.currentTimeMillis() - startTime));
        dataOutputStream.close();
        socket.close();
        inputStream.close();

    }
}
