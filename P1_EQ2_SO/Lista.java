package P1_EQ2_SO;
import java.util.LinkedList;

/*
 * 
 *      Manejo de cola de procesos general y 
 *      cola de procesos en RAM
 * 
 */


public class Lista {
    private LinkedList<Proceso> colaPListos; //cola general
    private LinkedList<Proceso> colaPListosEjecucion;  // cola para los cargadoe ram 

    private int capacidadRAM;
    private int memoriaDispo;

    public Lista(int capacidadRAM){
        this.capacidadRAM = capacidadRAM;
        this.memoriaDispo = capacidadRAM;
        colaPListos = new LinkedList<>();
        colaPListosEjecucion = new LinkedList<>();
    }


    public void agregarProceso(Proceso proceso){
        colaPListos.add(proceso);
    }

    //Para cargar por primera vez en la cola de la ram comprobamos si hay espacio
    public boolean cargarEnRam(Proceso proceso){
        if(proceso.getTamaño() <= memoriaDispo){
            colaPListosEjecucion.add(proceso);
            memoriaDispo -= proceso.getTamaño();
            return true;
        }
        return false;
    }

    /*
     * 
     * Esta función esta en duda por si la ocupan en la logica
     * si hay procesos esperando por la limitacion de espacio 
     * y necesitamos cargarlos en ram cuando se libere otra vez
     * 


     public void rellenarRam(){
        for(int i = 0; i < colaPListos.size(); ){
            Proceso p = colaPListos.get(i);

            if(p.getTamaño() <= memoriaDispo){
                colaPListosEjecucion.add(p);
                memoriaDispo -= p.getTamaño();
                colaPListos.remove(i);
            }else{
                i--;
            }
        }

     }
    */

    public LinkedList<Proceso> getcolaPListos(){
        return colaPListos;
    }

    public LinkedList<Proceso> getcolaPListosEjecucion(){
        return colaPListosEjecucion;
    }

    public int getMemoriaDispo(){
        return memoriaDispo;
    }

    public int getEspacioRam(){
        return capacidadRAM;
    }

    public boolean colaPListosVacia(){
        return colaPListos.isEmpty();
    }

    public boolean colaPListosEjecucionVacia(){
        return colaPListosEjecucion.isEmpty();
    }


    public void eliminarProceso(Proceso proceso){
        colaPListos.remove(proceso);
    }

    public void eliminarProcesoRam(Proceso proceso){
        colaPListosEjecucion.remove(proceso);
    }

    public Proceso obtenerProcesos(int ID){ //Podemso obtenerlos por ID
        for(Proceso p : colaPListos){
            if(p.getId() == ID) {
                return p;
            }
        }
        return null;
    }

    public int tamañocolaPListosEjecucion(){
        return colaPListosEjecucion.size();
    }

    public int tamañocolaPListos(){
        return colaPListos.size();
    }


    //Para limpiar cola general
    
    public void limpiar(){
        colaPListos.clear();
    }

    //Para ir removiendo procesos de la ram

    public void liberarRam(Proceso proceso){
        if(colaPListosEjecucion.remove(proceso)){
            memoriaDispo += proceso.getTamaño();
            //rellenarRam();
        }
    }

}
