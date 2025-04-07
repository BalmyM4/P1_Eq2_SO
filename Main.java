

import java.util.LinkedList;

import P1_EQ2_SO.Auxiliar;
import P1_EQ2_SO.Proceso;
import P1_EQ2_SO.Lista;

public class Main {
    public static void main(String[] args) {
        
        // Objeto auxiliar para leer el archivo CSV
        Auxiliar auxiliar = new Auxiliar();

        // Procesos leidos desde el archivo CSV
        LinkedList<Proceso> procesos = auxiliar.leerProcesos("Ej1.csv");

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
