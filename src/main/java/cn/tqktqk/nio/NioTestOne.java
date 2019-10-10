package cn.tqktqk.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;
import java.util.Random;

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
 * @date ：Created in 2019/9/30 6:57 下午
 * @description：
 * @modified By：
 * @version:
 */
public class NioTestOne {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(10);


        for (int i = 0;i<5;i++){
            int random = new SecureRandom().nextInt(50);
            intBuffer.put(random);
        }

        System.out.println("before flip limit:"+intBuffer.limit());
        intBuffer.flip();//翻转状态 写入---》读
        System.out.println("after flip limit:"+intBuffer.limit());


        while (intBuffer.hasRemaining()){//是否有剩余
            System.out.println(String.format("position:%d,limit:%d,capacity:%d",intBuffer.position(),intBuffer.limit(),intBuffer.capacity()).toString());
            System.out.println("value:"+intBuffer.get()+"\t");
        }
    }
}
