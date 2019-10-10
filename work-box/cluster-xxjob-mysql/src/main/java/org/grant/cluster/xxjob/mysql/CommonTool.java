package org.grant.cluster.xxjob.mysql;

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * grant
 * 10/10/2019 10:51 AM
 * 描述：
 */
@Slf4j
public class CommonTool {
    public static final String INSERT_SQL = "INSERT INTO `xxl_job`(`task_name`, `done`) VALUES (?, ?)";
    public static final String UPDATE_SQL = "UPDATE `xxl_job` SET `done` = ?, `exec_ip` = ?, `expire_time` = ? WHERE `task_name` = ?";
    public static final String UPDATE_SQL_START = "UPDATE `xxl_job` SET `done` = ? WHERE `task_name` = ?";
    public static final String SELECT_SQL = "SELECT id, task_name, done, exec_ip, expire_time FROM xxl_job where task_name = ?";
    public static final String SELECT_SQL_COUNT = "SELECT count(1) FROM xxl_job where task_name = ?";
    public static final long expire = 60 * 1000; // 60s

    private static String IP = null;

    public static final Integer READY = -1;
    public static final Integer START = 0;
    public static final Integer FINISH = 1;

    public static String getIpAddress() {
        if (IP != null) return IP;
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            IP = ip.getHostAddress();
                            return IP;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.debug("IP地址获取失败", e);
        }
        IP =  "127.0.0.1";
        return IP;
    }
}
