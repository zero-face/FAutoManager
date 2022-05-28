import java.lang.management.ManagementFactory;

/**
 * @Author Verge
 * @Date 2021/12/23 15:51
 * @Version 1.0
 */
public class TestGetPID {
    public static void main(String[] args) {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);

        String pid = name.split("@")[0];
        System.out.println(pid);
    }
}
