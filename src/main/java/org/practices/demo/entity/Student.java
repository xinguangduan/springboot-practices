package org.practices.demo.entity;

import lombok.Builder;
import lombok.Cleanup;
import lombok.Data;
import lombok.extern.java.Log;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Data
//@Value
//@Setter
//@Getter
@Data
@Builder
public class Student {
    private String name;
    private int age;
    private boolean sex;

}


@Log
class Test {
    public static void main(String[] args) {
        Student std = Student.builder().name("zhangsan").sex(true).age(111).build();
        System.out.println(std);
    }
}

class CleanupExample {
    public static void main(String[] args) throws IOException {
        @Cleanup
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[1024]);
        @Cleanup
        InputStream in = new FileInputStream("c:\\test\\a.txt");
        @Cleanup
        OutputStream out = new FileOutputStream("b.txt");
        byte[] b = new byte[10000];
        while (true) {
            int r = in.read(b);
            if (r == -1)
                break;
            out.write(b, 0, r);
        }
    }
}

class Demo1{
    public static void main(String[] args) {
        String ele = "1";
        List<String> authcChannels = Stream.of(ele).collect(Collectors.toList());
        System.out.println(authcChannels);
    }
}


