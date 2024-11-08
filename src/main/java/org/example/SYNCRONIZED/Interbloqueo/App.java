package org.example.SYNCRONIZED.Interbloqueo;

import java.io.IOException;

public class App
{
    public static void main(String[] args) throws IOException, InterruptedException {
        CompteBancari compteA = new CompteBancari(2145, 1450);
        CompteBancari compteB = new CompteBancari(7459, 2700);

        System.out.println("[Programa principal] Inici compteA '" + compteA.getIdCompte() + "' amb saldo de " + compteA.getSaldo());
        System.out.println("[Programa principal] Inici compteB '" + compteB.getIdCompte() + "' amb saldo de " + compteB.getSaldo());

        CaixerBanc obj1 = new CaixerBanc(compteA, compteB, 1);
        CaixerBanc obj2 = new CaixerBanc(compteB, compteA, 2);

        Thread fil1 = new Thread(() -> {
            try
            {
                obj1.transferirDinersOK(compteA, compteB, 100);
            }
            catch (IOException | InterruptedException e)
            {
                e.printStackTrace();
            }
        }, "Hombre");

        Thread fil2 = new Thread(() -> {
            try
            {
                obj2.transferirDinersOK(compteB, compteA, 100);
            }
            catch (IOException | InterruptedException e)
            {
                e.printStackTrace();
            }
        }, "Mujer");

        fil1.start();
        fil2.start();

        fil1.join();
        fil2.join();

        System.out.println("[Programa principal] Finalizacion del programa principal '" + compteA.getIdCompte() + "' con saldo " + compteA.getSaldo());
        System.out.println("[Programa principal] Finalizacion del programa principal '" + compteB.getIdCompte() + "' con saldo " + compteB.getSaldo());
    }
}