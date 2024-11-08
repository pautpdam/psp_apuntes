package org.example.SYNCRONIZED.MetodosEstaticos;

public class ContadorSinSincronizacion
{
    private static int contador = 0;

    // Metodo estatico con sincronizacion
/*    public synchronized static void incrementar()
    {
        contador++;
        System.out.println("Contador con sincronizacion, valor: " + contador);
    } */

    public static void incrementar()
    {
        System.out.println("Preparando para incrementar...");

        synchronized(ContadorSinSincronizacion.class)
        {
            contador++;
            System.out.println("Contador con sincronizacion en el bloque, valor: " + contador);
        }

        System.out.println("Incremento completado");
    }

    public static int obtenerContador()
    {
        return contador;
    }
}

class TestSincronizacionSin
{
    public static void main(String[] args) throws InterruptedException {
        Runnable tareaIncremento = () ->
        {
            for (int i = 0; i < 10; i++)
            {
                ContadorSinSincronizacion.incrementar();
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };

        Thread hilo1 = new Thread(tareaIncremento);
        Thread hilo2 = new Thread(tareaIncremento);
        Thread hilo3 = new Thread(tareaIncremento);

        hilo1.start();
        hilo2.start();
        hilo3.start();

        hilo1.join();
        hilo2.join();
        hilo3.join();

        System.out.println("Valor final del contador (con sincronizacion) -> " + ContadorSinSincronizacion.obtenerContador());
    }
}