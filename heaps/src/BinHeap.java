import java.util.ArrayList;

public class BinHeap {
    ArrayList<Integer> heap;

    public BinHeap() {
        this.heap = new ArrayList<Integer>();
    }

    /**
     * Devuelve el índice del padre de un nodo.
     * @param index Índice del nodo hijo.
     * @return Índice del nodo padre.
     */
    private int parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * Devuelve el índice del hijo izquierdo de un nodo.
     * @param index Índice del nodo padre.
     * @return Índice del nodo hijo izquierdo.
     */
    private int leftChild(int index) {
        return 2 * index + 1;
    }

    /**
     * Devuelve el índice del hijo derecho de un nodo.
     * @param index Índice del nodo padre.
     * @return Índice del nodo hijo derecho.
     */
    private int rightChild(int index) {
        return 2 * index + 2;
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
        int left_child = leftChild(max_index);
        int right_child = rightChild(max_index);

        if (left_child < heap.size() && heap.get(left_child) > heap.get(max_index)) {
            max_index = left_child;
        }

        if (right_child < heap.size() && heap.get(right_child) > heap.get(max_index)) {
            max_index = right_child;
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
