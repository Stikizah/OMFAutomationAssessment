package OMF.utilities.mobile;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ThreadLocalRandom;

public class AppiumWebServiceBuilder {
    public static final int LEAST_PORT = 2400;

    public static Integer nextFreePort() {
        Integer port = (int) (Math.random() * 8000) + LEAST_PORT;
        while (true) {
            try (ServerSocket endpoint = new ServerSocket(port)) {
                System.out.println("Free port to be used for device test: " + port);
                return port;
            } catch (IOException e) {
                port = ThreadLocalRandom.current().nextInt();
            }
        }
    }

    public AppiumDriverLocalService CreateAppiumService(String platformName) {

        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        if (platformName.equalsIgnoreCase("Android")) {
            builder.usingAnyFreePort()
                    .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, nextFreePort().toString())
                    .withArgument(AndroidServerFlag.CHROME_DRIVER_PORT, nextFreePort().toString());

        } else if (platformName.equalsIgnoreCase("iOS")) {
            builder.usingAnyFreePort();
        }

        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
        if (!service.isRunning()) {
            service.start();
        }
        return service;
    }

}
