package OMF.utilities.mobile;

import OMF.models.MobileDeviceCapabilities;
import com.testinium.deviceinformation.DeviceInfo;
import com.testinium.deviceinformation.DeviceInfoImpl;
import com.testinium.deviceinformation.device.DeviceType;
import com.testinium.deviceinformation.exception.DeviceNotFoundException;
import com.testinium.deviceinformation.model.Device;
import org.openqa.selenium.Platform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnectedDeviceDetection {

    public List<MobileDeviceCapabilities> getCombinedConnectedList() {
        List<MobileDeviceCapabilities> combinedList = new ArrayList<>();
        List<MobileDeviceCapabilities> connectedAndroidDevices = new ConnectedDeviceDetection().getAttachedAndroidDeviceList();
        if (Platform.getCurrent().name().contains("MAC")) {
            List<MobileDeviceCapabilities> connectedIOSDevices = new ConnectedDeviceDetection().getAttachedIOSDeviceList();
            combinedList.addAll(connectedIOSDevices);
        }
        combinedList.addAll(connectedAndroidDevices);
        return combinedList;
    }

    public MobileDeviceCapabilities getFirstAttachedAndroidDevice() {
        DeviceInfo deviceInfo = new DeviceInfoImpl(DeviceType.ANDROID);
        MobileDeviceCapabilities connectedAndroidDevice = null;
        Device device;
        try {
            deviceInfo.anyDeviceConnected();
            device = deviceInfo.getFirstDevice();
            connectedAndroidDevice = new MobileDeviceCapabilities();
            connectedAndroidDevice.mobilePlatform = device.getDeviceProductName();
            connectedAndroidDevice.platformVersion = device.getProductVersion();
            connectedAndroidDevice.deviceName = device.getModelNumber();
            connectedAndroidDevice.udid = device.getUniqueDeviceID();
            connectedAndroidDevice.browserType = "chrome";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DeviceNotFoundException e) {
            e.printStackTrace();
        }
        return connectedAndroidDevice;
    }

    public MobileDeviceCapabilities getFirstAttachedIOSDevice() {
        DeviceInfo deviceInfo = new DeviceInfoImpl(DeviceType.IOS);
        MobileDeviceCapabilities connectedIOSDevice = null;
        Device device;
        try {
            deviceInfo.anyDeviceConnected();
            device = deviceInfo.getFirstDevice();
            connectedIOSDevice = new MobileDeviceCapabilities();
            connectedIOSDevice.mobilePlatform = device.getDeviceProductName();
            connectedIOSDevice.platformVersion = device.getProductVersion();
            connectedIOSDevice.deviceName = device.getModelNumber();
            connectedIOSDevice.udid = device.getUniqueDeviceID();
            connectedIOSDevice.browserType = "safari";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DeviceNotFoundException e) {
            e.printStackTrace();
        }
        return connectedIOSDevice;
    }

    public List<MobileDeviceCapabilities> getAttachedAndroidDeviceList() {
        List<MobileDeviceCapabilities> devices;
        devices = new ArrayList<>();

        DeviceInfo deviceInfo = new DeviceInfoImpl(DeviceType.ANDROID);
        try {
            if (deviceInfo.anyDeviceConnected()) {
                List<Device> deviceList = deviceInfo.getDevices();

                for (Device device : deviceList) {
                    MobileDeviceCapabilities connectedAndroidDevice = new MobileDeviceCapabilities();
                    connectedAndroidDevice.mobilePlatform = device.getDeviceProductName();
                    connectedAndroidDevice.platformVersion = device.getProductVersion();
                    connectedAndroidDevice.deviceName = device.getModelNumber();
                    connectedAndroidDevice.udid = device.getUniqueDeviceID();
                    connectedAndroidDevice.browserType = "chrome";
                    devices.add(connectedAndroidDevice);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DeviceNotFoundException e) {
            e.printStackTrace();
        }
        return devices;
    }

    public List<MobileDeviceCapabilities> getAttachedIOSDeviceList() {
        List<MobileDeviceCapabilities> devices;
        devices = new ArrayList<>();

        DeviceInfo deviceInfo = new DeviceInfoImpl(DeviceType.IOS);
        try {
            if (deviceInfo.anyDeviceConnected()) {
                List<Device> deviceList = deviceInfo.getDevices();

                for (Device device : deviceList) {
                    MobileDeviceCapabilities connectedAndroidDevice = new MobileDeviceCapabilities();
                    connectedAndroidDevice.mobilePlatform = device.getDeviceProductName();
                    connectedAndroidDevice.platformVersion = device.getProductVersion();
                    connectedAndroidDevice.deviceName = device.getModelNumber();
                    connectedAndroidDevice.udid = device.getUniqueDeviceID();
                    connectedAndroidDevice.browserType = "safari";
                    devices.add(connectedAndroidDevice);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DeviceNotFoundException e) {
            e.printStackTrace();
        }
        return devices;
    }
}
