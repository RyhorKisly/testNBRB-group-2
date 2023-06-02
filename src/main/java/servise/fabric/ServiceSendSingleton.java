package servise.fabric;

import controllerrs.fabric.SendControllerSingleton;
import servise.ServiceSend;
import servise.api.IServiceSend;

public class ServiceSendSingleton {
    private static volatile IServiceSend instance;

    private ServiceSendSingleton() {
    }

    public static IServiceSend getInstance() {
        if(instance==null){
            synchronized (ServiceSendSingleton.class){
                if(instance==null){
                    instance = new ServiceSend(SendControllerSingleton.getInstance());
                }
            }
        }
        return instance;
    }
}
