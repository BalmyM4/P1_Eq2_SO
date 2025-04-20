package P1_EQ2_SO;

public class RoundRobin {
    private int quantum;
    private int tiempoTotal;
    private int tiempoEsperaTotal;
    private int tiempoRespuestaTotal;
    private int tiempoEjecucionTotal;
    private int procesosCompletados;
    private Lista listaProcesos;
    private int totalProcesosIniciales;

    public RoundRobin(Lista lista, int quantum) {
        this.quantum = quantum;
        this.listaProcesos = lista;
        this.tiempoTotal = 0;
        this.tiempoEsperaTotal = 0;
        this.tiempoRespuestaTotal = 0;
        this.tiempoEjecucionTotal = 0;
        this.procesosCompletados = 0;
        this.totalProcesosIniciales = lista.getProcesos().size();
    }

    public void simular() {
        System.out.println("\nIniciando simulación Round Robin (Quantum: " + quantum + ")\n");
        while (!simulacionTerminada()) {
            System.out.println("\n--- Ciclo T = " + tiempoTotal + " ---");
            // 1. Manejar nuevos procesos que llegan en este ciclo
            manejarLlegadasProcesos();
            // 2. Cargar procesos a RAM si hay espacio
            cargarProcesosRAM();
            // 3. Ejecutar procesos en CPU
            ejecutarCPU();
            // 4. Actualizar tiempos de espera
            actualizarTiemposEspera();
            tiempoTotal++;
        }
        mostrarMetricasFinales();
    }

    private boolean simulacionTerminada() {
        return listaProcesos.colaPListosVacia() &&
                listaProcesos.colaPListosEjecucionVacia() &&
                procesosCompletados == totalProcesosIniciales;
    }

    private void manejarLlegadasProcesos() {
        for (Proceso p : listaProcesos.getProcesos()) {
            if (p.getT_llegada() == tiempoTotal) {
                //System.out.println(" * " + p.getNombre() + " llegó al sistema en T=" + tiempoTotal);
                listaProcesos.agregarProceso(p);
                p.set_Ultimoascenso(tiempoTotal);
            }
        }
    }

    private void cargarProcesosRAM() {
        while (!listaProcesos.colaPListosVacia()) {
            Proceso p = listaProcesos.getcolaPListos().getFirst();
            // Solo cargar procesos que ya han llegado
            if (p.getT_llegada() > tiempoTotal) {
                break;
            }
            if (p.getTamaño() <= listaProcesos.getMemoriaDispo()) {
                Proceso procesoCargado = listaProcesos.popColaPListo();
                listaProcesos.cargarEnRam(procesoCargado);
                System.out.println(" * " + procesoCargado.getNombre() +
                                    " (" + procesoCargado.getTamaño() + "MB) subió a RAM. " +
                                    listaProcesos.getMemoriaDispo() + "MB restantes");
            } else {
                System.out.println(" * No hay RAM suficiente para " + p.getNombre() +
                                    " (necesita " + p.getTamaño() + "MB, disponible " +
                                    listaProcesos.getMemoriaDispo() + "MB)");
                break;
            }
        }
    }

    private void ejecutarCPU() {
        if (!listaProcesos.colaPListosEjecucionVacia()) {
            Proceso procesoActual = listaProcesos.getcolaPListosEjecucion().getFirst();
            // Registrar tiempo de respuesta si es la primera vez
            if (procesoActual.getT_respuesta() == -1) {
                procesoActual.set_Trespuesta(tiempoTotal);
                tiempoRespuestaTotal += tiempoTotal - procesoActual.getT_llegada();
                System.out.println(" * " + procesoActual.getNombre() +
                                    " responde por primera vez en T=" + tiempoTotal);
            }

            // Ejecutar proceso
            procesoActual.setRafagaAcum(procesoActual.getRafAcum() + 1);
            procesoActual.setContador(procesoActual.getContadorTurnos() + 1);
            System.out.println(" * Ejecutando " + procesoActual.getNombre() +
                                " (" + procesoActual.getRafAcum() + "/" +
                                procesoActual.getRafaga() + ") | Q=" +
                                procesoActual.getContadorTurnos());

            // Verificar si el proceso ha terminado
            if (procesoActual.getRafAcum() >= procesoActual.getRafaga()) {
                finalizarProceso(procesoActual);
            }
            // Verificar si se acabó el quantum
            else if (procesoActual.getContadorTurnos() % quantum == 0) {
                rotarProceso(procesoActual);
            }
        } else {
            System.out.println(" * CPU ocioso - No hay procesos en RAM");
        }
    }

    private void finalizarProceso(Proceso proceso) {
        // Calcular tiempo de espera
        int tiempoEspera = tiempoTotal - proceso.getT_llegada() - proceso.getRafaga();
        tiempoEsperaTotal += tiempoEspera;
        // Calcular tiempo de ejecución total
        tiempoEjecucionTotal += tiempoTotal - proceso.getT_llegada();
        // Liberar recursos
        listaProcesos.eliminarProcesoRam(proceso);
        procesosCompletados++;
        System.out.println(" * " + proceso.getNombre() + " completado. " +
                            "Liberando " + proceso.getTamaño() + "MB de RAM. " +
                            "Tiempo de espera: " + tiempoEspera);
    }

    private void rotarProceso(Proceso proceso) {
        if (listaProcesos.getcolaPListosEjecucion().size() > 1) {
            // Reiniciar contador de quantum para el proceso
            proceso.setContador(0);
            // Rotar el proceso al final de la cola
            Proceso procesoRotado = listaProcesos.popColaPListoEjecucion();
            listaProcesos.getcolaPListosEjecucion().addLast(procesoRotado);
            System.out.println(" * Quantum terminado. Rotando " + procesoRotado.getNombre());
            // Mostrar nueva cola de ejecución
            listaProcesos.imprimircolaPListosEjecucion();
        } else {
            // Solo hay un proceso, reiniciar su quantum
            proceso.setContador(0);
            System.out.println(" * Quantum terminado, pero solo hay un proceso en RAM. Continúa " + proceso.getNombre());
        }
    }

    private void actualizarTiemposEspera() {
        // Actualizar tiempos de espera para procesos en cola general
        for (Proceso p : listaProcesos.getcolaPListos()) {
            if (p.getT_llegada() <= tiempoTotal) {
                p.set_Ultimoascenso(tiempoTotal);
            }
        }
        // Actualizar tiempos de espera para procesos en RAM (excepto el que está en CPU)
        if (!listaProcesos.colaPListosEjecucionVacia()) {
            for (int i = 1; i < listaProcesos.getcolaPListosEjecucion().size(); i++) {
                Proceso p = listaProcesos.getcolaPListosEjecucion().get(i);
                p.set_Ultimoascenso(tiempoTotal);
            }
        }
    }

    private void mostrarMetricasFinales() {
        System.out.println("\n=== SIMULACIÓN COMPLETADA ===");
        System.out.println("Tiempo total de simulación: " + tiempoTotal);
        System.out.println("Procesos completados: " + procesosCompletados);
        if (procesosCompletados > 0) {
            System.out.println("\n--- Métricas Promedio ---");
            System.out.printf("Tiempo de ejecución promedio: %d / %d = %.2f\n",
                            tiempoEjecucionTotal, procesosCompletados, (double)tiempoEjecucionTotal/procesosCompletados);
            System.out.printf("Tiempo de espera promedio: %d / %d =  %.2f\n",
                            tiempoEsperaTotal, procesosCompletados, (double)tiempoEsperaTotal/procesosCompletados);
            System.out.printf("Tiempo de respuesta promedio: %d / %d = %.2f\n",
                            tiempoRespuestaTotal, procesosCompletados ,(double)tiempoRespuestaTotal/procesosCompletados);
        }
    }

    // Getters y Setters
    public int getQuantum() {
        return quantum;
    }
    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }
    public int getTiempoTotal() {
        return tiempoTotal;
    }
    public int getTiempoEsperaTotal() {
        return tiempoEsperaTotal;
    }
    public int getTiempoRespuestaTotal() {
        return tiempoRespuestaTotal;
    }
    public int getTiempoEjecucionTotal() {
        return tiempoEjecucionTotal;
    }
    public int getProcesosCompletados() {
        return procesosCompletados;
    }
}