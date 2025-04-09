package P1_EQ2_SO;
//import java.util.LinkedList;

public class RoundRobin {
    // Variables para el algoritmo
    private int quantum;
    private int quantumAct; // Lista de procesos
    // Variables para los calculos
    private int tiempoEspera;
    private int tiempoRespuesta;
    private int tiempoTotal;
    // Variables auxiliares
    private int procesosTerminados;
    // Constructor
    public RoundRobin(Lista lista, int quantum){
        this.quantum = quantum;
        this.tiempoTotal = 0;
        this.tiempoEspera = 0;
        this.tiempoRespuesta = 0;
        this.procesosTerminados = 0;
    }
    // Setters
    public void setQuantum(int quantum){
        this.quantum = quantum;
    }
    public void setTiempoTotal(int tiempoTotal){
        this.tiempoTotal = tiempoTotal;
    }
    public void setTiempoEspera(int tiempoEspera){
        this.tiempoEspera = tiempoEspera;
    }
    public void setTiempoRespuesta(int tiempoRespuesta){
        this.tiempoRespuesta = tiempoRespuesta;
    }
    public void setProcesosTerminados(int procesosTerminados){
        this.procesosTerminados = procesosTerminados;
    }

    // Getters
    public int getQuantum(){
        return quantum;
    }
    public int getTiempoTotal(){
        return tiempoTotal;
    }
    public int getTiempoEspera(){
        return tiempoEspera;
    }
    public int getTiempoRespuesta(){
        return tiempoRespuesta;
    }
    public int getProcesosTerminados(){
        return procesosTerminados;
    }

    // Metodos para el algoritmo
    public void asigarCPU( Proceso proceso ) {
        if (proceso.getRafagaFaltante() > 0) {
            proceso.setRafagaFaltante(proceso.getRafagaFaltante() - 1);
            quantumAct--;
            System.out.println("Proceso " + proceso.getNombre() + ": " + proceso.getRafagaFaltante() + " ms.");
            tiempoTotal++;
        }
        else {
            procesosTerminados++;
        }
    }
}