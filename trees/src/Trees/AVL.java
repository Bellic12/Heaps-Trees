package Trees;

import Nodes.Node;

public class AVL<T extends Comparable<T>> extends BST<T> {

    public AVL() {
        super();
    }

    @Override
    public int height(Node<T> node) {
        return (node == null) ? -1 : node.getHeight();
    }

    /**
     * Rota el subárbol enraizado en el nodo dado hacia la derecha.
     * @param node La raíz del subárbol a rotar.
     */
    private void rotateRight(Node<T> node) {
        Node<T> leftChild = node.getLeft();
        node.setLeft(leftChild.getRight());

        if (leftChild.getRight() != null) {
            leftChild.getRight().setParent(node);
        }

        leftChild.setParent(node.getParent());

        if (node.getParent() == null) {
            setRoot(leftChild);
        } else if (node == node.getParent().getRight()) {
            node.getParent().setRight(leftChild);
        } else {
            node.getParent().setLeft(leftChild);
        }

        leftChild.setRight(node);
        node.setParent(leftChild);
        updateHeight(node);
        updateHeight(leftChild);
    }

    /**
     * Rota el subárbol enraizado en el nodo dado hacia la izquierda.
     * @param node La raíz del subárbol a rotar.
     */
    private void rotateLeft(Node<T> node) {
        Node<T> rightChild = node.getRight();
        node.setRight(rightChild.getLeft());

        if (rightChild.getLeft() != null) {
            rightChild.getLeft().setParent(node);
        }

        rightChild.setParent(node.getParent());

        if (node.getParent() == null) {
            setRoot(rightChild);
        } else if (node == node.getParent().getLeft()) {
            node.getParent().setLeft(rightChild);
        } else {
            node.getParent().setRight(rightChild);
        }

        rightChild.setLeft(node);
        node.setParent(rightChild);
        updateHeight(node);
        updateHeight(rightChild);
    }

    /**
     * Actualiza la altura del nodo dado en función de las alturas de sus hijos.
     * @param node El nodo a actualizar.
     */
    private void updateHeight(Node<T> node) {
        int leftHeight = (node.getLeft() == null) ? 0 : node.getLeft().getHeight();
        int rightHeight = (node.getRight() == null) ? 0 : node.getRight().getHeight();
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
    }

    /**
     * Obtiene el factor de balance del nodo dado.
     * @param node El nodo a verificar.
     * @return El factor de balance del nodo.
     */
    private int getBalance(Node<T> node) {
        if (node == null) return 0;
        int leftHeight = (node.getLeft() == null) ? 0 : node.getLeft().getHeight();
        int rightHeight = (node.getRight() == null) ? 0 : node.getRight().getHeight();
        return leftHeight - rightHeight;
    }

    /**
     * Inserta un valor en el árbol AVL y rebalancea el árbol si es necesario.
     * @param key El valor a insertar.
     */
    @Override
    public void insert(T key) {
        super.insert(key);
        Node<T> node = find(key, getRoot());
        rebalance(node);
    }

    /**
     * Rebalancea el árbol comenzando desde el nodo dado hasta la raíz.
     * @param node El nodo desde el cual comenzar el rebalanceo.
     */
    private void rebalance(Node<T> node) {
        while (node != null) {
            updateHeight(node);
            int balance = getBalance(node);

            if (balance > 1) {
                rebalanceRight(node);
            } else if (balance < -1) {
                rebalanceLeft(node);
            }

            node = node.getParent();
        }
    }

    /**
     * Rebalancea el árbol cuando el factor de balance es mayor que 1.
     * @param node El nodo a rebalancear.
     */
    private void rebalanceRight(Node<T> node) {
        if (getBalance(node.getLeft()) < 0) {
            rotateLeft(node.getLeft());
        }
        rotateRight(node);
    }

    /**
     * Rebalancea el árbol cuando el factor de balance es menor que -1.
     * @param node El nodo a rebalancear.
     */
    private void rebalanceLeft(Node<T> node) {
        if (getBalance(node.getRight()) > 0) {
            rotateRight(node.getRight());
        }
        rotateLeft(node);
    }

    /**
     * Elimina un nodo del árbol AVL y rebalancea el árbol si es necesario.
     * @param node El nodo a eliminar.
     */
    @Override
    public void delete(Node<T> node) {
        super.delete(node);
        rebalance(node.getParent());
    }
}
