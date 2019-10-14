package cn.tqktqk.nio;

import java.nio.ByteBuffer;

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
 * @date ：Created in 2019/10/13 1:02 下午
 * @description：
 * @modified By：
 * @version:
 */
public class NioTestSix {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }
        for (int i = 0; i <byteBuffer.capacity(); i++) {
            System.out.print(byteBuffer.get(i)+",");
        }

        byteBuffer.position(1);
        byteBuffer.limit(8);
        ByteBuffer slice = byteBuffer.slice();

        for (int i = 0; i < slice.capacity(); i++) {
            byte b = slice.get(i);
            b+=1;
            slice.put(i,b);
        }
        System.out.println();
        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());
        for (int i = 0; i <byteBuffer.capacity(); i++) {
            System.out.print(byteBuffer.get(i)+",");
        }
    }
}
