package org.example.SYNCRONIZED.Interbloqueo;

import java.io.IOException;

public class CaixerBanc implements Runnable
{
    private CompteBancari compte;
    private int operacioAfer;

    private CompteBancari compteOpcional; // Interbloqueo

    public CaixerBanc(CompteBancari compte1, CompteBancari compte2, int operacioAfer)
    {
        this.compte = compte1;
        this.compteOpcional = compte2;
        this.operacioAfer = operacioAfer;
    }

    // METODO INGRESAR DINERO
    public void ingressar(float diners) throws IOException, InterruptedException
    {
        this.compte.ingressarCB(diners, Thread.currentThread().getName());
    }

    // METODO RETIRAR DINERO
    public void treure(float diners) throws IOException, InterruptedException
    {
        this.compte.treureCB(diners, Thread.currentThread().getName());
    }

    // Interbloqueo
    public void ingressar(CompteBancari compte, float diners) throws IOException, InterruptedException
    {
        this.compte.ingressarCB(diners, Thread.currentThread().getName());
    }

    // Interbloqueo
    public void treure(CompteBancari compte, float diners) throws IOException, InterruptedException
    {
        this.compte.treureCB(diners, Thread.currentThread().getName());
    }

    // Tenemos que hacer transferencias entre dos cuentas
    public void transferirDiners(CompteBancari compteOrigen, CompteBancari compteDesti, float quantitat) throws IOException, InterruptedException
    {
        synchronized(compteOrigen)
        {
            System.out.println(Thread.currentThread().getName() + " ha bloqueado la cuenta origen [" + compteOrigen.getIdCompte() + "]");

            Thread.sleep(1000);

            synchronized(compteDesti)
            {
                System.out.println(Thread.currentThread().getName() + " ha bloqueado la cuenta destino [" + compteDesti.getIdCompte() + "]");

                // Codigo sincronizado para realizar la transferencia
                if (compteOrigen.getSaldo() >= quantitat)
                {
                    compteOrigen.treureCB(quantitat, Thread.currentThread().getName());
                    compteDesti.ingressarCB(quantitat, Thread.currentThread().getName());
                }
            }
        }
    }

    // INTERBLOQUEO SOLUCION
    // Establecer un orden en la sincronizacion
    // Creamos dos objetos de tipo CompteBancari asignarle un orden
    // en funcion de su orden numerico para que el bloqueo se haga
    // siempre en el mismo orden independientemente de la llamada al metodo

    public void transferirDinersOK(CompteBancari cOrigen, CompteBancari cDesti, float quantitat) throws IOException, InterruptedException
    {
        CompteBancari compteA = null;
        CompteBancari compteB = null;

        if (cOrigen.getIdCompte() < cDesti.getIdCompte())
        {
            compteA = cOrigen;
            compteB = cDesti;
        }
        else if (cOrigen.getIdCompte() > cDesti.getIdCompte())
        {
            compteA = cDesti;
            compteB = cOrigen;
        }
        else
        {
            return;
        }

        // SINCRONIZACION ORDENADA
        synchronized(compteA)
        {
            synchronized(compteB)
            {
                if (cOrigen.getSaldo() >= quantitat)
                {
                    cOrigen.treureCB(quantitat, Thread.currentThread().getName());
                    cDesti.ingressarCB(quantitat, Thread.currentThread().getName());
                }
            }
        }
    }

    @Override
    public void run() {
        // Java permet sincronitzar només una part del codi dins d'ún método
        // Volem que s'execute en exclusió mútua
        try
        {
            switch (operacioAfer)
            {
                case 1:
                    synchronized(compte) // referenciar un objecte
                                         // SINCRONIZACION SOBRE EL OBJETO COMPARTIDO COMPTE
                    {
                        System.out.println("[" + Thread.currentThread().getName() + "] (1/2) Ingressant 400");
                        ingressar(400);
                        System.out.println("[" + Thread.currentThread().getName() + "] (1/2) Traguent 400");
                        treure(400);
                        break;
                    }
                default:
                synchronized(compte)
                {
                        System.out.println("[" + Thread.currentThread().getName() + "] (2/2) Traguent 600");
                        treure(600);
                        System.out.println("[" + Thread.currentThread().getName() + "] (2/2) Ingressant 200");
                        ingressar(200);
                        break;
                }
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
