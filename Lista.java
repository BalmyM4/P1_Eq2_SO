import java.util.LinkedList;

/*
 * 
 *      Manejo de cola de procesos general y 
 *      cola de procesos en RAM
 * 
 */


public class Lista {
    private LinkedList<Proceso> colaProcesos; //cola general
    private LinkedList<Proceso> colaRam;  // cola para los cargadoe ram 

    private int capacidadRAM;
    private int memoriaDispo;

    public Lista(int capacidadRAM){
        this.capacidadRAM = capacidadRAM;
        this.memoriaDispo = capacidadRAM;
        colaProcesos = new LinkedList<>();
        colaRam = new LinkedList<>();
    }


    public void agregarProceso(Proceso proceso){
        colaProcesos.add(proceso);
    }

    //Para cargar por primera vez en la cola de la ram comprobamos si hay espacio
    public boolean cargarEnRam(Proceso proceso){
        if(proceso.getTamaño() <= memoriaDispo){
            colaRam.add(proceso);
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
        for(int i = 0; i < colaProcesos.size(); ){
            Proceso p = colaProcesos.get(i);

            if(p.getTamaño() <= memoriaDispo){
                colaRam.add(p);
                memoriaDispo -= p.getTamaño();
                colaProcesos.remove(i);
            }else{
                i--;
            }
        }

     }
    */

    public LinkedList<Proceso> getColaProcesos(){
        return colaProcesos;
    }

    public LinkedList<Proceso> getColaRam(){
        return colaRam;
    }

    public int getMemoriaDispo(){
        return memoriaDispo;
    }

    public int getEspacioRam(){
        return capacidadRAM;
    }

    public boolean colaProcesosVacia(){
        return colaProcesos.isEmpty();
    }

    public boolean colaRamVacia(){
        return colaRam.isEmpty();
    }


    public void eliminarProceso(Proceso proceso){
        colaProcesos.remove(proceso);
    }

    public void eliminarProcesoRam(Proceso proceso){
        colaRam.remove(proceso);
    }

    public Proceso obtenerProcesos(int ID){ //Podemso obtenerlos por ID
        for(Proceso p : colaProcesos){
            if(p.getId() == ID) {
                return p;
            }
        }
        return null;
    }

    public int tamañoColaRam(){
        return colaRam.size();
    }

    public int tamañoColaProcesos(){
        return colaProcesos.size();
    }


    //Para limpiar cola general
    
    public void limpiar(){
        colaProcesos.clear();
    }

    //Para ir removiendo procesos de la ram

    public void liberarRam(Proceso proceso){
        if(colaRam.remove(proceso)){
            memoriaDispo += proceso.getTamaño();
            //rellenarRam();
        }
    }

}
