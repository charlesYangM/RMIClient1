package rmiForAutoPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CountDownLatch;

/**
 * created by charlesYang 2017/6/21
 */
public class AutoPackageClient {
    static ProcessBuilder builder = new ProcessBuilder();
    public static void main(String[] args) throws Exception {
//        String url820 = "rmi://172.16.8.20:1099/AutoPackage";
//        autoPackageByUrl(url820);
//        String url822 = "rmi://172.16.8.22:1099/AutoPackage";
//        autoPackageByUrl(url822);
//        String url823 = "rmi://172.16.8.23:1099/AutoPackage";
//        autoPackageByUrl(url823);
        sendAutoPackageToServerUsingThread();


    }

    /**
     * 使用三个线程向服务器发送请求
     * @throws Exception
     */
    public static void sendAutoPackageToServerUsingThread() throws Exception {
        /*三个服务器请求的协作，定义了这个变量，就可以知道三个线程是否都执行完毕
            这样就不用总是查看控制台的显示信息了，
        */
        CountDownLatch latch=new CountDownLatch(1);

//        autoPackageThread820 Thread820 = new autoPackageThread820(latch);
        autoPackageThread822 Thread822 = new autoPackageThread822(latch);
        autoPackageThread823 Thread823 = new autoPackageThread823(latch);
//        //创建线程1
//        Thread t1 = new Thread(Thread820);
//        t1.start();
        //创建线程2
        Thread t2 = new Thread(Thread822);
        t2.start();
//        创建线程2
//        Thread t3 = new Thread(Thread823);
//        t3.start();


        //await方法的意义在于，等待所有的线程的执行完毕才执行接下来的语句
        latch.await();

        System.out.println(latch.getCount());

        showCompleteRMI();
    }
    public static void showCompleteRMI() throws Exception {
        String showCompletecmd = "@mshta vbscript:msgbox(\"RMI通信完毕\",64,\"提示框Title\")(window.close)";
        ExcuteCmd(showCompletecmd);
    }

    /**
     * 运行cmd命令
     * @param cmd 产生的运行指令都在这里面
     * @throws Exception
     */
    public static void ExcuteCmd(String cmd) throws Exception{
        builder.command(
                "cmd.exe", "/c", cmd
        );
        builder.redirectErrorStream(true);
        //start() 执行命令
        Process p = builder.start();

        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream(),"gbk"));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            }
            System.out.println(line);
            if (line.contains("error")){
                System.exit(-1);
            }
        }

    }

    /**
     * 通过向指定的url发送客户端的rmi请求，得到一个与服务器端的代理并与之沟通
     * @param url 服务器端的IP地址以及端口
     *            例如："rmi://172.16.8.20:1099/AutoPackage"
     * @throws RemoteException
     * @throws NotBoundException
     * @throws MalformedURLException
     */
    public static void autoPackageByUrl(String url) throws RemoteException, NotBoundException, MalformedURLException {
        AutoPackage autoPackage = (AutoPackage) Naming.lookup(url);
        System.out.println(url+"---------打包完毕？：   "+autoPackage.startKafkaPackgeInstall());
    }

    /**
     * 以下定义了三个内部类用于实现多线程，其实，真正意义的多线程是创建多个线程执行同一段代码实现多个任务，
     * 但在这个地方写了三段代码，然后在main方法中创建了三个线程用于执行这三段代码，
     * 通过这样的方式，我们就可以向三个服务器同时发送请求，而不是一个接一个的发，满足了“我”的需求，管它的，哈哈，
     */
    static class autoPackageThread820 implements Runnable {
        CountDownLatch latch;
        public autoPackageThread820(CountDownLatch latch){
            this.latch = latch;
        }
        public void run() {
            String url820 = "rmi://172.16.8.20:1099/AutoPackage";
            try {
                autoPackageByUrl(url820);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }finally {
                System.out.println("820开始减数");
                latch.countDown();
            }
        }
    }


    static class autoPackageThread822 implements Runnable {
        CountDownLatch latch;
        public autoPackageThread822(CountDownLatch latch){
            this.latch = latch;
        }
        public void run() {
            String url822 = "rmi://172.16.8.22:1099/AutoPackage";
            try {
                autoPackageByUrl(url822);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            latch.countDown();

        }

    }
    static class autoPackageThread823 implements Runnable {
        CountDownLatch latch;
        public autoPackageThread823(CountDownLatch latch){
            this.latch = latch;
        }
        public void run() {
            String url823 = "rmi://172.16.8.23:1099/AutoPackage";
            try {
                autoPackageByUrl(url823);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            latch.countDown();
        }
    }
}