package tasks;

import java.math.BigInteger;

public class TaskCPU {
    public static void main(String[] args) {
        System.out.println("[TaskCPU] Calculando números primos grandes...");
        int encontrados = 0;
        BigInteger n = new BigInteger("1000000000000000"); // 10^15
        while (encontrados < 40) {
            if (n.isProbablePrime(50)) {
                encontrados++;
            }
            n = n.add(BigInteger.ONE);
        }
        System.out.println("[TaskCPU] Primos encontrados: " + encontrados);
    }
}
