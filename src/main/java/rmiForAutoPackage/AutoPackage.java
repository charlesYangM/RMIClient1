package rmiForAutoPackage;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by charles on 2017/6/21.
 *
 */
public interface AutoPackage extends Remote{
    public boolean startKafkaPackgeInstall() throws RemoteException;
}
