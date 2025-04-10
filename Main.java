

import java.util.LinkedList;

import P1_EQ2_SO.Auxiliar;
import P1_EQ2_SO.Proceso;
import P1_EQ2_SO.Lista;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n\nBienvenidos - Proyecto Simulador de Procesos - Round Robin\n");
        // Objeto auxiliar para leer el archivo CSV
        Auxiliar auxiliar = new Auxiliar();
        // Procesos leidos desde el archivo CSV
        LinkedList<Proceso> procesos = auxiliar.leerProcesos("Ej1.csv");
        // Impresión de los procesos obtendos desde el .csv
        System.out.println("\nLista de procesos leídos desde el CSV:");
        System.out.println("ID | T. Llegada | Tamaño | T. Rafaga");
        System.out.println("-------------------------------------");
        for (Proceso p : procesos) {
            System.out.printf(
                "%2d | %10d | %6d | %9d\n",
                p.getId(),
                p.getT_llegada(),
                p.getTamaño(),
                p.getRafaga()
            );
        }
        System.out.println("-------------------------------------");
        System.out.println("\nInicio de la simulación:\n");
        // Objeto que contiene nuestras estructuras de datos
        Lista edd = new Lista( 1024 );
        int clock = 0;    // Tiempo de reloj
        // Agregar procesos a la lista de procesos
        while ( true ) {
            // Verificar si el proceso ha llegado al sistema
            if ( !procesos.isEmpty() ) {
                if ( procesos.getFirst().getT_llegada() == clock ) {
                    Proceso p = procesos.removeFirst();
                    edd.agregarProceso( p );
                }
            }
            // Verificar si hay espacio en RAM para cargar el proceso
            if ( !edd.colaPListosVacia() ){
                if ( edd.getMemoriaDispo() > edd.getcolaPListos().getFirst().getTamaño() ) {
                    // Sacar proceso de la cola de listos
                    Proceso p = edd.popColaPListo();
                    // Cargar el proceso en RAM
                    edd.cargarEnRam( p );
                }
            }
            // Verificar si hay procesos en RAM para ejecutar
            if ( !edd.colaPListosEjecucionVacia() ) {
                // Sacar proceso de la cola de listos para ejecutar
                Proceso p = edd.getcolaPListosEjecucion().getFirst();
                // Verificar si el proceso ha llegado a su quantum
                if ( p.getRafagaFaltante() == p.getRafaga()) {
                }
            }

            clock++;
        }
    }
}
