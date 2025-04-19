import java.util.LinkedList;
import java.util.Scanner;
import P1_EQ2_SO.Auxiliar;
import P1_EQ2_SO.Proceso;
import P1_EQ2_SO.Lista;
import P1_EQ2_SO.RoundRobin;

public class Main {
    public static void main(String[] args) {
        int seleccion = 0;
        int capacidadRAM = 1024; // MB de RAM
        Scanner scan = new Scanner(System.in);
        Auxiliar auxiliar = new Auxiliar();
        LinkedList<Proceso> procesos = new LinkedList<>();

        System.out.println("\n\nBienvenidos - Proyecto Simulador de Procesos - Round Robin\n");
        
        // Menú de selección de entrada de datos
        System.out.println("Seleccione la manera en que desea iniciar la simulación\n");
        while (seleccion != 1 && seleccion != 2) {
            System.out.println("\t1) Introducir los datos mediante el teclado");
            System.out.println("\t2) Leer los datos de un archivo predeterminado\n");
            System.out.print("Tu selección: ");
            seleccion = scan.nextInt();

            switch (seleccion) {
                case 1:
                    procesos = auxiliar.tecladoprocesos(scan, capacidadRAM);
                    System.out.println("\nLista de procesos leídos desde el teclado\n");
                    break;
                case 2:
                    procesos = auxiliar.leerProcesos("Ej1.csv");
                    System.out.println("\nLista de procesos leídos desde el CSV\n");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor seleccione 1 o 2\n");
                    break;
            }
        }

        // Mostrar resumen de procesos
        System.out.println("\nResumen de procesos:");
        System.out.println("ID | Nombre | T. Llegada | Tamaño | T. Rafaga");
        System.out.println("---------------------------------------------");
        for (Proceso p : procesos) {
            System.out.printf(
                "%2d | %6s | %10d | %6d | %9d\n",
                p.getId(),
                p.getNombre(),
                p.getT_llegada(),
                p.getTamaño(),
                p.getRafaga()
            );
        }
        System.out.println("---------------------------------------------\n");

        // Configuración inicial
        int quantum = 3; // Valor de quantum por defecto
        
        System.out.print("Ingrese el valor del quantum (deje en blanco para usar 3): ");
        scan.nextLine(); // Limpiar buffer
        String inputQuantum = scan.nextLine();
        if (!inputQuantum.isEmpty()) {
            quantum = Integer.parseInt(inputQuantum);
        }

        // Crear estructuras de datos
        Lista edd = new Lista(capacidadRAM, procesos);
        
        // Agregar todos los procesos a la cola de listos
        /*for (Proceso p : procesos) {
            edd.agregarProceso(p);
        }*/

        // Iniciar simulación
        System.out.println("\n\nIniciando simulación con:");
        System.out.println("- Capacidad RAM: " + capacidadRAM + " MB");
        System.out.println("- Quantum: " + quantum + " unidades de tiempo\n");
        
        RoundRobin rr = new RoundRobin(edd, quantum);
        rr.simular();

        scan.close();
    }
}