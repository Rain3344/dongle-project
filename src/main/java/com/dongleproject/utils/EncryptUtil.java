package com.dongleproject.utils;

import com.FTSafe.Dongle;


public class EncryptUtil {

    public static String checkDongle() {
        byte[] dongleInfo = new byte[1024];
        int[] count = new int[1];
        long[] handle = new long[1];
        int nRet = 0;
        int i = 0;
        Dongle dongle = new Dongle();

        nRet = dongle.Dongle_Enum(dongleInfo, count);
        if (nRet != Dongle.DONGLE_SUCCESS) {
            return "NO Dongle ";
        }

        int index = 0;
        short[] ver = new short[1];
        short[] type = new short[1];
        byte[] birthday = new byte[8];
        int[] agent = new int[1];
        int[] pid = new int[1];
        int[] uid = new int[1];
        byte[] hid = new byte[8];
        int[] isMother = new int[1];
        int[] devType = new int[1];

        // 读取设备锁的 ID，检查指定设备锁是否存在
        String myPidStr = "4FE8AF48";
        int myPid = (int) Long.parseLong(myPidStr, 16);
        nRet = dongle.GetDongleInfo(dongleInfo, 0, ver, type, birthday, agent, pid, uid, hid, isMother, devType);

        if (pid[0] != myPid) {
            return "Dongle PID error ";
        }

        // 打开设备锁
        nRet = dongle.Dongle_Open(handle, index);
        if (nRet != Dongle.DONGLE_SUCCESS) {
            return "Dongle Open error ";
        }

        // 时间限制         如果设置了到期时间 到期后 这个函数会返回 F0000017。判断一下这个返回值。
        int[] nRemain = new int[1];
        // 验证用户密码
        String strPin = "12345678";
        nRet = dongle.Dongle_VerifyPIN(handle[0], dongle.FLAG_USERPIN, strPin, nRemain);
        if (nRet != Dongle.DONGLE_SUCCESS) {
            dongle.Dongle_Close(handle[0]);
            return "Dongle VerifyPIN error " + ",锁已过期！";
        }

        // 限制调用次数
        byte[] m = new byte[117];
        byte[] c = new byte[128];
        int[] outLen = new int[1];
        outLen[0] = 128;
        for (i = 0; i < 117; i++) m[i] = (byte) i;
        // 1. 私钥加密，加密文件 0x1010
        nRet = dongle.Dongle_RsaPri(handle[0], 0x1011, Dongle.FLAG_ENCODE, m, 117, c, outLen);
        if (nRet != Dongle.DONGLE_SUCCESS) {
            if (nRet != 0xF000000B) {
                dongle.Dongle_Close(handle[0]);
                return "Dongle RsaPri error ";
            }
            dongle.Dongle_Close(handle[0]);
            return "count-based limit " + ",调用次数已用完！";
        }

        // 关闭锁
        nRet = dongle.Dongle_Close(handle[0]);
        if (nRet != Dongle.DONGLE_SUCCESS) {
            return "Dongle Close error " ;
        }

        //具体方法执行

        return "true";
    }
}