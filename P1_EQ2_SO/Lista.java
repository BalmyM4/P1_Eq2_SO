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
    private LinkedList<Proceso> colaPListosEjecucion;  // cola para los cargados en ram 

    private int capacidadRAM; //Valor de la capacidad de memoria (asignable)
    private int memoriaDispo; //Valor de memoria disponible (para verificaciones)
    private int idActual = 10000; //5 digitos de base para los ID

    //Constructor 
    public Lista(int capacidadRAM){
        this.capacidadRAM = capacidadRAM;
        this.memoriaDispo = capacidadRAM;
        colaPListos = new LinkedList<>();
        colaPListosEjecucion = new LinkedList<>();
    }

    /*
     * Se agrega la generacion de ID unico por proceso
     * manera facil por orden de generación
     */
    public int generarID(){
        return idActual++;
    }


     /*
      * 
      Metodo de integración de procesos a la cola 
      */
    public void agregarProceso(Proceso proceso){
        colaPListos.add(proceso);
        System.out.println("\tProceso" + proceso.getNombre() + "agregado a la cola de procesos\n " );
        imprimircolaPListos();
    }

    /*
     * Para cargar procesos en la cola de procesos listos
     * de la ram comprobamos si hay espacio (boleana)
     * 
    */
    public boolean cargarEnRam(Proceso proceso){
        if(proceso.getTamaño() <= memoriaDispo){
            colaPListosEjecucion.add(proceso);
            memoriaDispo -= proceso.getTamaño();
            System.out.println("\tProceso" + proceso.getNombre() + "sube a RAM\n" );
            imprimircolaPListosEjecucion();
            return true;
        }else{
            System.out.println("\n\tNo hay espacio suficiente en la RAM" );
        }
        return false; //Se regresa solo si no hay espacio
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

    /*
     * Getters para el acceso a ambas colas
     */
    public LinkedList<Proceso> getcolaPListos(){
        return colaPListos;
    }

    public LinkedList<Proceso> getcolaPListosEjecucion(){
        return colaPListosEjecucion;
    }

    /*
     * Obtención de valores de memoria disponible
     * y de espacio disponible
     */

    public int getMemoriaDispo(){
        return memoriaDispo;
    }

    public int getEspacioRam(){
        return capacidadRAM;
    }

    /*
     * Verificación de colas vacias
     * 
     */
    public boolean colaPListosVacia(){
        return colaPListos.isEmpty();
    }

    public boolean colaPListosEjecucionVacia(){
        return colaPListosEjecucion.isEmpty();
    }

    /*
     * Metodos de eliminación de procesos especificos
     */
    public void eliminarProceso(Proceso proceso){
        colaPListos.remove(proceso);
        imprimircolaPListos();

    }

    public void eliminarProcesoRam(Proceso proceso){
        if(colaPListosEjecucion.remove(proceso)){
            memoriaDispo += proceso.getTamaño();
            imprimircolaPListosEjecucion();
        }
    }

    /*
     * Pop de procesos de la cola de procesos listos
     * Pop de procesos de la cola de procesos listos para ejecucion
     */

    public Proceso popColaPListo(){
        Proceso procesoAux = colaPListos.removeFirst();
        imprimircolaPListos();
        return procesoAux;
    }

    public Proceso popColaPListoEjecucion (){
        Proceso procesoAux = colaPListosEjecucion.removeFirst();
        imprimircolaPListosEjecucion();
        return procesoAux;
    }


    /*
     * Podremos buscar un proceso por su ID unico en la cola general
     */
    public Proceso obtenerProcesos(int ID){ 
        for(Proceso p : colaPListos){
            if(p.getId() == ID) {
                return p;
            }
        }
        return null;
    }

    /*
     * Obtención de tamaño de las colar
     */
    public int tamañocolaPListosEjecucion(){
        return colaPListosEjecucion.size();
    }

    public int tamañocolaPListos(){
        return colaPListos.size();
    }


    /*
     * Limpieza de la cola en general
     */
    
    public void limpiar(){
        colaPListos.clear();
    }

    /*
     * Para ir removiendo procesos de la ram 
     * y liberar continuamente el espacio
     */
    public void liberarRam(Proceso proceso){
        if(colaPListosEjecucion.remove(proceso)){
            memoriaDispo += proceso.getTamaño();
            //rellenarRam();
        }
    }

    /*
     * Método extra de verificacion de IDs en ambas colas
     * (boleano)
     */
    public boolean verificaciónID(int id){
        for(Proceso proceso : colaPListos){
            if(proceso.getId() == id) return true;
        }
        for(Proceso proceso : colaPListosEjecucion){
            if(proceso.getId() == id) return true;
        }
        return false;
    }


    /*
     * Se agregan métodos de impresión de ambas colar 
     * métodos adaptables segun sea el caso
     */
    public void imprimircolaPListos(){
        if(colaPListos.isEmpty()){
            System.out.println("\nLa cola de procesos listos esta vacía");
            return;
        }
        System.out.println("\n=====================================================");
        System.out.println("\n\tProcesos listos y en espera");
        System.out.println("\n=====================================================");
        for (int i = colaPListos.size() -1 ; i>=0; i--) {
            Proceso p = colaPListos.get(i);
            System.out.printf("[%05d  |  %-10s]   ", p.getId(), p.getNombre());
        }
    }


    public void imprimircolaPListosEjecucion(){
        if(colaPListosEjecucion.isEmpty()){
            System.out.println("\nLa cola de procesos listos esta vacía");
            return;
        }
        System.out.println("\n=====================================================");
        System.out.println("\n\tProcesos en ejecución");
        System.out.println("\n=====================================================");
        for (int i = colaPListosEjecucion.size() -1 ; i>=0; i--) {
            Proceso p = colaPListosEjecucion.get(i);
            System.out.printf("[%05d  |  %-10s]   ", p.getId(), p.getNombre());
        }
    }
}