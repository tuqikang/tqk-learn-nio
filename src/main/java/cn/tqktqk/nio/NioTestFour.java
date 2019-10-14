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
 * @date ：Created in 2019/10/12 12:48 下午
 * @description：
 * @modified By：
 * @version:
 */
public class NioTestFour {
    public static void main(String[] args) throws Exception{
        FileOutputStream fos = new FileOutputStream("OutTest");
        FileInputStream fis = new FileInputStream("InTest");

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        FileChannel inChannel = fis.getChannel();
//        inChannel.read(byteBuffer);
        FileChannel outChannel = fos.getChannel();
//        byteBuffer.flip();
//        outChannel.write(byteBuffer);

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
