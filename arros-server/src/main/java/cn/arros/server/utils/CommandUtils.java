package cn.arros.server.utils;

import cn.hutool.core.io.IoUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Verge
 * @Date 2021/11/3 0:56
 * @Version 1.0
 */
public class CommandUtils {

    private final static List<String> PREFIX = new ArrayList<>();

    static {
        if (isWindows()) {
            PREFIX.add("powershell");
            PREFIX.add("/C");
        } else {
            PREFIX.add("/bin/bash");
            PREFIX.add("-c");
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name")
                .toLowerCase().startsWith("windows");
    }

    public static List<String> prepare(String... cmds) {
        List<String> cmdList = Arrays.stream(cmds).collect(Collectors.toList());
        cmdList.addAll(0, PREFIX);
        return cmdList;
    }

    // TODO
    public static void execute(File dir, String... cmds) throws IOException {
        List<String> cmdList = prepare(cmds);
        ProcessBuilder builder = new ProcessBuilder();
        Process process = builder
                .command(cmdList)
                .directory(dir)
                .start();
        String res = IoUtil.read(process.getInputStream(), Charset.defaultCharset());
        String error = IoUtil.read(process.getErrorStream(), Charset.defaultCharset());
        System.out.println("res");
        System.out.println(res);
        System.out.println("error");
        System.out.println(error);
    }
}
