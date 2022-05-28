package cn.arros.server.service;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author Verge
 * @Date 2021/11/6 22:07
 * @Version 1.0
 */
class DeployServiceTest {

    @Test
    void run() throws IOException {

        SshClient sshClient = SshClient.setUpDefaultClient();
        sshClient.start();

        String username = "root";
        String host = "";
        int port = 22;


        ClientSession session = sshClient.connect(username, host, port)
                .verify(2, TimeUnit.MINUTES)
                .getClientSession();

        session.addPasswordIdentity("");
        session.auth().verify(2, TimeUnit.MINUTES);
        //String res = session.executeRemoteCommand("java -jar cultural-relics-database-0.0.1-SNAPSHOT.jar");
        String res = session.executeRemoteCommand("nohup java -jar cultural-relics-database-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 & echo $!");
        // String res = session.executeRemoteCommand("java -jar cultural-relics-database-0.0.1-SNAPSHOT.jar");
        System.out.println("输出");
        System.out.println(res);
    }

}