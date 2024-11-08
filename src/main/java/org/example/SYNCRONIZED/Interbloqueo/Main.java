package org.example.SYNCRONIZED.Interbloqueo;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        CompteBancari compte1 = new CompteBancari(0);
        CompteBancari compte2 = new CompteBancari(0);
        System.out.println("[Programa Principal] | Saldo -> " + compte2.getSaldo() + "€");

        CaixerBanc obj1 = new CaixerBanc(compte1, compte2, 1);
        CaixerBanc obj2 = new CaixerBanc(compte1, compte2, 2);

        Thread fil1 = new Thread(obj1, "home");
        Thread fil2 = new Thread(obj2, "dona");

        fil1.start();
        fil2.start();

        fil1.join();
        fil2.join();

        System.out.println("[Programa Principal] = Finalizado | Saldo -> " + compte2.getSaldo() + "€");

        // INTERBLOQUEIG (DEADLOCK)
    }
}