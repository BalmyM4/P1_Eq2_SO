

import java.util.LinkedList;
import java.util.Scanner;
import P1_EQ2_SO.Auxiliar;
import P1_EQ2_SO.Proceso;
import P1_EQ2_SO.Lista;

public class Main {
    public static void main(String[] args) {

        int seleccion = 0;   //variable auxiliar para la selección del usuario

        //Declaración de scanner para capturar datos por teclado
        Scanner scan = new Scanner(System.in);

        LinkedList<Proceso> procesos = new LinkedList<>();

        System.out.println("\n\nBienvenidos - Proyecto Simulador de Procesos - Round Robin\n");
        
        // Objeto auxiliar para leer el archivo CSV
        Auxiliar auxiliar = new Auxiliar();

        System.out.println("Seleccione la manera en que desea iniciar la simulación\n");

        while (seleccion != 1 && seleccion != 2) {

            System.out.println("\t1)Introducir los datos mediante el teclado.\n\t2)Leer los datos de un archivo predeterminado\n");

            System.out.print("Tu selección: ");
            seleccion = scan.nextInt();
    
            switch (seleccion) {
                case 1://Generación de procesos por teclado

                    procesos = auxiliar.tecladoprocesos(scan);
                    // Impresión de los procesos obtendos desde el teclado
                    System.out.println("\nLista de procesos leídos desde el teclado\n");

                break;
    
                case 2: //Generación de procesos por CSV

                    // Procesos leidos desde el archivo CSV
                    procesos = auxiliar.leerProcesos("Ej1.csv");
                    // Impresión de los procesos obtendos desde el .csv
                    System.out.println("\nLista de procesos leídos desde el CSV\n");

                break;
            
                default: 
                    System.out.println("Por favor introduzca uno de los números que correspondan con las opciones en pantalla\n");
                break;
            }
        }

        System.out.println("ID | Nombre | T. Llegada | Tamaño | T. Rafaga");
        System.out.println("-------------------------------------");
        for (Proceso p : procesos) {
            System.out.printf(
                "%2d |   %s   | %10d | %6d | %9d\n",
                p.getId(),
                p.getNombre(),
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
