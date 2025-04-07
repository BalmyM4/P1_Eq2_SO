/*
 * 
 *      Manejo de procesos para las listas de procesos
 * 
 * 
 */

public class Proceso {
    private int id;
    private String nombre;
    private int tamaño;
    private int t_rafaga;
    private int t_llegada;

    // Auxiliares para los calculos de tiempos y turnos
    private int ultimoAsenso;
    private int rafaga_acum;
    private int turnos;
    private boolean estado;
    private int t_respuesta;


    public Proceso(int id, String nombre, int tamaño, int rafaga, int t_llegada){
        this.id = id;
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.t_rafaga = rafaga;
        this.t_llegada = t_llegada;
        this.rafaga_acum = 0;
        this.turnos = 0;
        this.estado = false;
        this.t_respuesta = -1;
    }

    //Getters 

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

    public int getT_llegada(){
        return t_llegada;
    }

    public int get_Ultimoascenso(){
        return ultimoAsenso;
    }

    public int getRafAcum(){
        return rafaga_acum;
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


    // Setters 
    public void setRafagaAcum(int rafaga_acum){
        this.rafaga_acum = rafaga_acum;
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

}