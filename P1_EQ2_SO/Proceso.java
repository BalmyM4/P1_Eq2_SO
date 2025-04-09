package P1_EQ2_SO;

/*
 *
 *      Manejo de procesos para las listas de procesos
 *
 *
 */

public class Proceso {
    private static int idActual = 0;

    private int id;          //Identificacion unica del proceso
    private String nombre;   //Nombre en cada proceso (asignable)
    private int tamaño;      //Tamaño de memoria que ocupa (asignable)
    private int t_rafaga;    //Tiempo total de raaga del proceso(asigmnable) y fijo
    private int t_llegada;   //Tiempo de llegada al sistema


    // Auxiliares para los calculos de tiempos y turnos
    private int ultimoAsenso;       //Ultimo tiempo regitrado de subida
    private int t_rafaga_acum;      //Rafaga acumulada (T ejecutado)
    private int t_rafaga_faltante;  //Tiempo auxiliar
    private int turnos;             //Numero de veces qe se ha subido
    private boolean estado;         //Estado del proceso (true = activo, false = inactivo)
    private int t_respuesta;        //Tiempo de primer uso en CPU


    /*
     * Constructor de inicializacion de un proceso
     */
    public Proceso(int id, String nombre, int tamaño, int rafaga, int t_llegada){
        this.id = id;
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.t_rafaga = rafaga;
        this.t_llegada = t_llegada;

        this.t_rafaga_faltante = 0;
        this.t_rafaga_acum = 0;
        this.turnos = 0;
        this.estado = false;
        this.t_respuesta = -1;  //Por si no ha respondido aún
    }


    /*
     * Getters de cada propiedad del proceso
     */

    public int getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public int getTamaño(){
        return tamaño;
    }

    public int getRafaga(){
        return t_rafaga;
    }

    public int getRafagaFaltante(){
        return t_rafaga_faltante;
    }

    public int getT_llegada(){
        return t_llegada;
    }

    public int get_Ultimoascenso(){
        return ultimoAsenso;
    }

    public int getRafAcum(){
        return t_rafaga_acum;
    }

    public int getContadorTurnos(){
        return turnos;
    }

    public boolean isEstado(){
        return estado;
    }

    public int getT_respuesta(){
        return t_respuesta;
    }
    public static int getIdActual(){
        return idActual;
    }


    /*
     * Setters de cada propiedad del proceso
     */ 
    
    public void setRafagaAcum(int rafaga_acum){
        this.t_rafaga_acum = rafaga_acum;
    }

    public void setRafagaFaltante(int t_rafaga_faltante){
        this.t_rafaga_faltante = t_rafaga_faltante;
    }

    public void setContador(int contador){
        this.turnos = contador;
    }

    public void setEstado(boolean estado){
        this.estado = estado;
    }

    public void set_Trespuesta(int t_respuesta){
        this.t_respuesta = t_respuesta;
    }

    public void set_Ultimoascenso(int t){
        this.ultimoAsenso = t;
    }
    public static void aumentarIdActual(){
        idActual++;
    }

}




