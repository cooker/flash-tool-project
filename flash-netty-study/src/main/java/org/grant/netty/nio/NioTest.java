package org.grant.netty.nio;

import com.google.common.base.Functions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * grant
 * 12/9/2019 4:08 PM
 * 描述：
 */
public class NioTest {
    public static void main(String[] args) throws IOException {
        IntStream stream = makeIntsm();
        stream.flatMap(i->IntStream.of(i)).findFirst().ifPresent(c->{
            System.out.println("range 1,10 find first = " + c);
        });

        stream = makeIntsm();
        stream.skip(2).findFirst().ifPresent(c->{
            System.out.println("skip 2 ~~ 1,10 find first = " + c);
        });

        stream = makeIntsm();
        int sum = stream.limit(2).reduce(0, (a, b)-> a+b);
        System.out.println("reduce a+b >> limit 2 ~~ 1,10 = " + sum);

        stream = makeIntsm();
        boolean isAnyatch = stream.anyMatch(i->i%2==0);
        System.out.println("anyMatch %2==0 1,10 = " + isAnyatch);
    }


    public static IntStream makeIntsm(){
        return IntStream.range(1, 10);
    }
}