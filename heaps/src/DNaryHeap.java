import java.util.ArrayList;

public class DNaryHeap {
    private ArrayList<Integer> heap;
    private int d;

    public DNaryHeap(int d) {
        this.heap = new ArrayList<Integer>();
        this.d = d;
    }

    /**
     * Devuelve el índice del padre de un nodo.
     * @param index Índice del nodo hijo.
     * @return Índice del nodo padre.
     */
    private int parent(int index) {
        return (index - 1) / d;
    }

    /**
     * Devuelve el índice del k-ésimo hijo de un nodo.
     * @param index Índice del nodo padre.
     * @param k Número del hijo (1 a d).
     * @return Índice del k-ésimo hijo.
     */
    private int child(int index, int k) {
        return d * index + k;
    }

    /**
     * Inserta un valor en la cola de prioridad.
     * @param value Valor a insertar.
     */
    public void insert(int value) {
        this.heap.add(value);
        siftUp(heap.size() - 1);
    }

    /**
     * Realiza el proceso de sift-up para mantener la propiedad del heap.
     * @param index Índice del nodo a ajustar.
     */
    private void siftUp(int index) {
        while (index > 0 && heap.get(parent(index)) < heap.get(index)) {
            swap(parent(index), index);
            index = parent(index);
        }
    }

    /**
     * Intercambia dos elementos en el heap.
     * @param x Índice del primer elemento.
     * @param y Índice del segundo elemento.
     */
    private void swap(int x, int y) {
        int temp = heap.get(x);
        heap.set(x, heap.get(y));
        heap.set(y, temp);
    }

    /**
     * Extrae y devuelve el valor máximo de la cola de prioridad.
     * @return Valor máximo.
     */
    public int extractMax() {
        int result = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        siftDown(0);
        return result;
    }

    /**
     * Realiza el proceso de sift-down para mantener la propiedad del heap.
     * @param index Índice del nodo a ajustar.
     */
    private void siftDown(int index) {
        int max_index = index;

        for (int i = 1; i <= d; i++) {
            int child_index = child(index, i);
            if (child_index < heap.size() && heap.get(child_index) > heap.get(max_index)) {
                max_index = child_index;
            }
        }

        if (index != max_index) {
            swap(index, max_index);
            siftDown(max_index);
        }
    }

    /**
     * Devuelve el valor máximo de la cola de prioridad sin extraerlo.
     * @return Valor máximo.
     */
    public int getMax() {
        return heap.get(0);
    }

    /**
     * Cambia la prioridad de un elemento en el heap.
     * @param index Índice del elemento a cambiar.
     * @param new_value Nuevo valor del elemento.
     */
    public void changePriority(int index, int new_value) {
        int old_value = heap.get(index);
        heap.set(index, new_value);

        if (new_value > old_value) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    /**
     * Elimina un elemento del heap.
     * @param index Índice del elemento a eliminar.
     */
    public void remove(int index) {
        heap.set(index, getMax() + 1);
        siftUp(index);
        extractMax();
    }

    /**
     * Imprime el contenido del heap.
     */
    public void print() {
        System.out.print("[");
        for (int i = 0; i < heap.size(); ++i) {
            System.out.print(heap.get(i));
            if (i < heap.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
