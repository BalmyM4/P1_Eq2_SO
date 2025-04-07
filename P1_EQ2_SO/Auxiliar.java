package P1_EQ2_SO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

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
