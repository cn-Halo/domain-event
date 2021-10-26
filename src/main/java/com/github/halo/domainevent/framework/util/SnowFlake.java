package com.github.halo.domainevent.framework.util;

/**
 * @author yzm
 * @date 2021/2/2 3:14 下午
 *     <p>Twitter的snowFlake算法实现
 */
public class SnowFlake {

  /**
   * 使用一个 64 bit 的 long 型的数字作为全局唯一 id 第一个部分是1 个
   *
   * <p>第一个部分是1 个 bit：0，这个是无意义的。由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0
   *
   * <p>第二个部分是 41 个 bit：表示的是时间戳。该时间戳存储的是时间截的差值（当前时间截 -
   * 开始时间截)得到的值，这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T
   * = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69
   *
   * <p>第三个部分是 5 个 bit：表示的是机房 id，10001。
   *
   * <p>第四个部分是 5 个 bit：表示的是机器 id，1 1001。
   *
   * <p>第五个部分是 12 个 bit：表示的序号，就是某个机房某台机器上这一毫秒内同时生成的 id 的序号，0000 00000000。
   */
  private static final long startStamp = 1612417102554L;

  private static final long SEQUENCE_BIT = 12;
  private static final long MACHINE_BIT = 5;
  private static final long DATA_CENTER_BIT = 5;
  private static final long TIMESTAMP_BIT = 41;

  private static final long MACHINE_SHIFT = SEQUENCE_BIT;
  private static final long DATA_CENTER_SHIFT = SEQUENCE_BIT + MACHINE_BIT;
  private static final long TIMESTAMP_SHIFT = SEQUENCE_BIT + MACHINE_BIT + DATA_CENTER_BIT;

  private long sequence = 0;
  private long machineId = 0;
  private long dataCenterId = 0;
  private long lastTimeStamp = -1L;

  public SnowFlake(long dataCenterId, long machineId) {
    if (dataCenterId < 0 || dataCenterId >= (1 << DATA_CENTER_BIT)) {
      throw new RuntimeException("dataCenterId的范围值为[" + 0 + "," + (1 << DATA_CENTER_BIT) + ")");
    }
    if (machineId < 0 || machineId >= (1 << MACHINE_BIT)) {
      throw new RuntimeException("machineId的范围值为[" + 0 + "," + (1 << MACHINE_BIT) + ")");
    }
    this.dataCenterId = dataCenterId;
    this.machineId = machineId;
  }

  public synchronized long nextId() {
    long currentTimeStamp = getTimeStamp();
    // 说明发生了时钟回拨
    if (currentTimeStamp < lastTimeStamp) {
      throw new RuntimeException("发生了时钟回拨，无法生成id");
    } else if (currentTimeStamp == lastTimeStamp) {
      // 说明是同一个毫秒内的请求
      // 如果本次请求跟上次请求在同一个毫秒内，并且序列号生成已经满了（同以毫秒最多生成4096个sequence）， 则让该线程自旋到下一个毫秒内
      if (sequence == (1 << SEQUENCE_BIT)) {
        currentTimeStamp = nextTimeStamp();
        sequence = 0;
      } else
        // 还没满就自增
        sequence++;

    } else
      // 说明不是同一个毫秒内的请求
      sequence = 0;

    lastTimeStamp = currentTimeStamp;

    return (currentTimeStamp - startStamp) << TIMESTAMP_SHIFT
        | dataCenterId << DATA_CENTER_SHIFT
        | machineId << MACHINE_SHIFT
        | sequence;
  }

  public long nextTimeStamp() {
    long timeStamp;
    while ((timeStamp = getTimeStamp()) <= lastTimeStamp)
      ;
    return timeStamp;
  }

  public long getTimeStamp() {
    return System.currentTimeMillis();
  }

  public static void main(String[] args) {
    long i = -1L ^ (-1L << 12); //
    System.out.println(i);
    System.out.println(1 & i);
    System.out.println((1 << SEQUENCE_BIT) - 1);
    //  0000 0000 0000 0000 0000 00|0|0 0000 0000 0000 0000 0000 0000 1110 1000 0110 0100

    //  00 0110 0100 0000 0000 0000 0000 0000 00
    System.out.println(System.currentTimeMillis() - startStamp);
    ;
    System.out.println(59492 << 22);

    SnowFlake snowFlake = new SnowFlake(31, 1);
    for (int j = 0; j < 4097; j++) {
      System.out.println(snowFlake.nextId());
    }
  }
}
