package P1_EQ2_SO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Auxiliar {

    public void decoracion( int valor ) {
        switch (valor) {
            case 1:
                System.err.println("\n===========================================================================\n");
                break;
            
            case 2:
                System.err.println("\n---------------------------------------------------------------------------\n");
                break;

            case 3:
                System.err.println("\n***************************************************************************\n");
                break;

            default:
                break;
        }
    }

    public LinkedList<Proceso> tecladoprocesos( Scanner scan, int capacidadRAM ){

        LinkedList<Proceso> procesos = new LinkedList<>();

        int numProcesos; //Variable que indica el número de procesos en la simulación
        String nombre;
        int tamaño = 0;
        int t_rafaga;
        int t_llegada;
        System.out.print("Introduzca el número de procesos que desea en su simulación: ");
        numProcesos = scan.nextInt();
        
        for(int i = 0; i < numProcesos; i++ ){
            System.out.print("Introduzca el nombre del proceso "+(i+1)+": ");
            nombre = scan.next();

            do {
                if (tamaño > capacidadRAM) {
                    System.out.println("El tamaño de un solo proceso no puede rebasar la capacidad de RAm total (RAM="+capacidadRAM+")");
                }
                System.out.print("Introduzca el tamaño del proceso "+(i+1)+": ");
                tamaño = scan.nextInt();
            } while (tamaño > capacidadRAM);

            System.out.print("Introduzca el tiempo de ejecución del proceso "+(i+1)+": ");
            t_rafaga = scan.nextInt();
            System.out.print("Introduzca el tiempo de llegada del proceso "+(i+1)+": ");
            t_llegada = scan.nextInt();

            Proceso newProceso = new Proceso(
                Proceso.getIdActual(),          // ID
                nombre,                     // Nombre
                tamaño,   // Tamaño
                t_rafaga,   // Rafaga
                t_llegada    // Tiempo de llegada
            );
            procesos.add(newProceso);
            Proceso.aumentarIdActual();// Aumentar el ID actual
            
        } 


        return procesos;
    }

    public LinkedList<Proceso> leerProcesos( String nomArchivo ) {

        LinkedList<Proceso> procesos = new LinkedList<>();

        String linea = null;

        // Leer el archivo CSV
        try (BufferedReader br = new BufferedReader(new FileReader(nomArchivo))) {

            // Leer la primera línea (encabezados)
            String encabezados = br.readLine();

            while ((linea = br.readLine()) != null) {

                String[] valores = linea.split(",");

                Proceso newProceso = new Proceso(
                        Proceso.getIdActual(),          // ID
                        valores[0],                     // Nombre
                        Integer.parseInt(valores[1]),   // Tamaño
                        Integer.parseInt(valores[2]),   // Rafaga
                        Integer.parseInt(valores[3])    // Tiempo de llegada
                    );
                procesos.add(newProceso);
                Proceso.aumentarIdActual();         // Aumentar el ID actual
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return procesos;
    }
}
