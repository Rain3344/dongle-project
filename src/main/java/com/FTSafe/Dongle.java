package com.FTSafe;


public class Dongle {

    public static final int CONST_PID = 0xFFFFFFFF;   //默认PID

    public static final String CONST_USERPIN = "12345678";  //默认用户PIN
    public static final String CONST_ADMINPIN = "FFFFFFFFFFFFFFFF"; //默认管理员PIN

    //通讯协议类型
    public static final int PROTOCOL_HID = 0; //hid协议
    public static final int PROTOCOL_CCID = 1; //ccid协议

    //文件类型
    public static final int FILE_DATA = 1; //数据文件
    public static final int FILE_PRIKEY_RSA = 2; //RSA私钥文件
    public static final int FILE_PRIKEY_ECCSM2 = 3; //ECC私钥文件SM2格式(SM2是国密算法基于ECC实现)
    public static final int FILE_KEY = 4; //SM4或3DES密钥文件
    public static final int FILE_EXE = 5; //可执行文件

    //LED状态控制
    public static final int LED_OFF = 0; //关闭
    public static final int LED_ON = 1; //开启
    public static final int LED_BLINK = 2;//闪烁

    //PIN类型标识
    public static final int FLAG_USERPIN = 0;//用户PIN
    public static final int FLAG_ADMINPIN = 1;//管理员PIN

    //加解密标识
    public static final int FLAG_ENCODE = 0;//加密
    public static final int FLAG_DECODE = 1; //解密

    //HASH算法类型
    public static final int FLAG_HASH_MD5 = 0; //MD5     输出长度16字节
    public static final int FLAG_HASH_SHA1 = 1;//SHA1    输出长度20字节
    public static final int FLAG_HASH_SM3 = 2; //SM3     输出长度32字节


    //更新功能类型
    public static final int UPDATE_FUNC_CreateFile = 1; //创建文件
    public static final int UPDATE_FUNC_WriteFile = 2; //写入文件
    public static final int UPDATE_FUNC_DeleteFile = 3; //删除文件
    public static final int UPDATE_FUNC_FileLic = 4; //设置文件权限
    public static final int UPDATE_FUNC_SeedCount = 5; //限制种子计数
    public static final int UPDATE_FUNC_DownloadExe = 6; //下载可执行文件
    public static final int UPDATE_FUNC_UnlockUserPin = 7; //解锁用户PIN
    public static final int UPDATE_FUNC_Deadline = 8;//设置截止日期


    //返回码定义
    public static final int DONGLE_SUCCESS = 0x00000000;            // 操作成功
    public static final int DONGLE_NOT_FOUND = 0xF0000001;          // 未找到加密锁
    public static final int DONGLE_INVALID_HANDLE = 0xF0000002;     // 无效句柄
    public static final int DONGLE_INVALID_PARAMETER = 0xF0000003;  // 参数错误
    public static final int DONGLE_COMM_ERROR = 0xF0000004;           // 通讯错误
    public static final int DONGLE_INSUFFICIENT_BUFFER = 0xF0000005;// 缓冲区不足
    public static final int DONGLE_NOT_INITIALIZED = 0xF0000006;       // 尚未初始化 (需要设置PID)
    public static final int DONGLE_ALREADY_INITIALIZED = 0xF0000007;// 已经初始化 (重复设置PID)
    public static final int DONGLE_ADMINPIN_NOT_CHECK = 0xF0000008; // 管理员权限未验证
    public static final int DONGLE_USERPIN_NOT_CHECK = 0xF0000009;  // 用户权限未验证
    public static final int DONGLE_INCORRECT_PIN = 0xF000FF00;       // 密码错误 (还有2次尝试机会)
    public static final int DONGLE_PIN_BLOCKED = 0xF000000A;           // PIN被锁定
    public static final int DONGLE_ACCESS_DENIED = 0xF000000B;       // 访问被拒绝
    public static final int DONGLE_FILE_EXIST = 0xF000000E;           // 文件已存在
    public static final int DONGLE_FILE_NOT_FOUND = 0xF000000F;       // 文件不存在
    public static final int DONGLE_READ_ERROR = 0xF0000010;         // 读取错误
    public static final int DONGLE_WRITE_ERROR = 0xF0000011;        // 写入错误
    public static final int DONGLE_FILE_CREATE_ERROR = 0xF0000012;  // 创建文件失败
    public static final int DONGLE_FILE_READ_ERROR = 0xF0000013;    // 读取文件失败
    public static final int DONGLE_FILE_WRITE_ERROR = 0xF0000014;   // 写入文件失败
    public static final int DONGLE_FILE_DEL_ERROR = 0xF0000015;     // 删除文件失败
    public static final int DONGLE_FAILED = 0xF0000016;             // 操作失败
    public static final int DONGLE_CLOCK_EXPIRE = 0xF0000017;       // 时钟已过期
    public static final int DONGLE_ERROR_UNKNOWN = 0xFFFFFFFF;      // 未知错误

    public native int Dongle_Enum(byte[] dongleInfo, int[] count);

    public native int Dongle_Open(long[] handle, int index);

    public native int Dongle_ResetState(long handle);

    public native int Dongle_Close(long handle);

    public native int Dongle_GenRandom(long handle, int len, byte[] random);

    public native int Dongle_LEDControl(long handle, int flag);

    public native int Dongle_SwitchProtocol(long handle, int flag);

    public native int Dongle_RFS(long handle);

    public native int Dongle_CreateFile(long handle, int fileType, int fileID, byte[] fileAttr);

    public native int Dongle_WriteFile(long handle, int fileType, int fileID, int offset, byte[] data, int dataLen);

    public native int Dongle_ReadFile(long handle, int fileID, int offset, byte[] outData, int dataLen);

    public native int Dongle_DownloadExeFile(long handle, byte[] exeFileInfo, int count);

    public native int Dongle_RunExeFile(long handle, int fileID, byte[] inOutData, int inOutDataLen, int[] mainRet);

    public native int Dongle_DeleteFile(long handle, int fileType, int fileID);

    public native int Dongle_ListFile(long handle, int fileType, byte[] fileList, int[] dataLen);

    public native int Dongle_GenUniqueKey(long handle, int seedLen, byte[] seed, String[] pid, String[] adminPin);

    public native int Dongle_VerifyPIN(long handle, int flag, String pin, int[] remainCount);

    public native int Dongle_ChangePIN(long handle, int flag, String oldPin, String newPin, int tryCount);

    public native int Dongle_ResetUserPIN(long handle, String adminPin);

    public native int Dongle_SetUserID(long handle, int userID);

    public native int Dongle_GetDeadline(long handle, int[] time);

    public native int Dongle_SetDeadline(long handle, int time);

    public native int Dongle_GetUTCTime(long handle, int[] time);

    public native int Dongle_ReadData(long handle, int offset, byte[] data, int dateLen);

    public native int Dongle_WriteData(long handle, int offset, byte[] data, int dataLen);

    public native int Dongle_ReadShareMemory(long handle, byte[] data);

    public native int Dongle_WriteShareMemory(long handle, byte[] data, int datelen);

    public native int Dongle_RsaGenPubPriKey(long handle, int priFileID, byte[] pubBakup, int[] pubLen, byte[] priBakup, int[] priLen);

    public native int Dongle_RsaPri(long handle, int priFileID, int flag, byte[] inData, int inDataLen, byte[] outData, int[] outDataLen);

    public native int Dongle_RsaPub(long handle, int flag, byte[] pubKey, byte[] inData, int inDataLen, byte[] outData, int[] outDataLen);

    public native int Dongle_EccGenPubPriKey(long handle, int priFileID, byte[] pubBakup, int[] pubLen, byte[] priBakup, int[] priLen);

    public native int Dongle_EccSign(long handle, int priFileID, byte[] hashData, int hashDataLen, byte[] outData);

    public native int Dongle_EccVerify(long handle, byte[] pubKey, byte[] hashdata, int hashDataLen, byte[] sign);

    public native int Dongle_SM2GenPubPriKey(long handle, int priFileID, byte[] pubBakup, int[] pubLen, byte[] priBakup, int[] priLen);

    public native int Dongle_SM2Sign(long handle, int priFileID, byte[] hashData, int hashDataLen, byte[] outData);

    public native int Dongle_SM2Verify(long handle, byte[] pubKey, byte[] hashdata, int hashDataLen, byte[] sign);

    public native int Dongle_TDES(long handle, int keyFileID, int flag, byte[] inData, byte[] outData, int dataLen);

    public native int Dongle_SM4(long handle, int keyFileID, int flag, byte[] inData, byte[] outData, int dataLen);

    public native int Dongle_HASH(long handle, int flag, byte[] inData, int inDataLen, byte[] hash);

    public native int Dongle_Seed(long handle, byte[] seed, int seedLen, byte[] outData);

    public native int Dongle_LimitSeedCount(long handle, int count);

    public native int Dongle_GenMotherKey(long handle, byte[] moterData);

    public native int Dongle_RequestInit(long handle, byte[] request);

    public native int Dongle_GetInitDataFromMother(long handle, byte[] request, byte[] initData, int[] dataLen);

    public native int Dongle_InitSon(long handle, byte[] initData, int dataLen);

    public native int Dongle_SetUpdatePriKey(long handle, byte[] priKey);

    public native int Dongle_MakeUpdatePacket(long handle, String shid, int func, int fileType, int fileID, int offset, byte[] buffer, int bufferLen, byte[] uPubKey, byte[] outData, int[] outDataLen);

    public native int Dongle_MakeUpdatePacketFromMother(long handle, String shid, int func, int fileType, int fileID, int offset, byte[] buffer, int bufferLen, byte[] outData, int[] outDataLen);

    public native int Dongle_Update(long handle, byte[] updateData, int dataLen);

    public native int GetDongleInfo(byte[] dongleInfo, int index, short[] ver, short[] type, byte[] birthday, int[] agent, int[] pid, int[] uid, byte[] hid, int[] isMother, int[] devType);

    public native int Convert_DATA_LIC_To_Buffer(short readPriv, short writePriv, byte[] buffer, int[] bufferLen);

    public native int Convert_PRIKEY_LIC_To_Buffer(int count, byte callPriv, byte isDecOnRAM, byte isReset, byte[] buffer, int[] bufferLen);

    public native int Convert_KEY_LIC_To_Buffer(int privEnc, byte[] buffer, int[] bufferLen);

    public native int Convert_EXE_LIC_To_Buffer(short privExe, byte[] buffer, int[] bufferLen);

    public native int Convert_DATA_FILE_ATTR_To_Buffer(int size, byte[] dataLic, int dataLicLen, byte[] buffer, int[] bufferLen);

    public native int Convert_PRIKEY_FILE_ATTR_To_Buffer(short type, short size, byte[] prikeyLic, int prikeyLicLen, byte[] buffer, int[] bufferLen);

    public native int Convert_KEY_FILE_ATTR_To_Buffer(int size, byte[] keyLic, int keyLicLen, byte[] buffer, int[] bufferLen);

    public native int Convert_EXE_FILE_ATTR_To_Buffer(short size, byte[] exeLic, int exeLicLen, byte[] buffer, int[] bufferLen);

    public native int Get_DATA_FILE_LIST_Info(byte[] fileList, int fileListLen, int index, short[] fileID, int[] fileSize, short[] readPriv, short[] writePriv);

    public native int Get_EXE_FILE_LIST_Info(byte[] fileList, int fileListLen, int index, short[] fileID, short[] fileSize, short[] callPriv);

    public native int Get_KEY_FILE_LIST_Info(byte[] fileList, int fileListLen, int index, short[] fileID, int[] fileSize, int[] encPriv);

    public native int Get_PRIKEY_FILE_LIST_Info(byte[] fileList, int fileListLen, int index, short[] fileID, short[] type, short[] fileSize, int[] count, byte[] priv, byte[] isDecOnRAM, byte[] isReset);

    public native int Add_EXE_FILE_INFO_To_Buffer(byte[] fileInfoBuffer, int[] fileInfoBufferLen, int index, short fileID, short fileSize, byte filePriv, byte[] exeBuffer);

    public native int Clear_EXE_FILE_INFO_Buffer(byte[] fileInfoBuffer, int count);

    //Convert Struct to Buffer
    //Convert Buffer to Struct
    static {
        System.loadLibrary("Dongle_java");
    }
}
