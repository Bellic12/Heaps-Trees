package Trees;

import java.util.LinkedList;
import Interfaces.Tree;
import Nodes.Node;

public class BST<T extends Comparable<T>> implements Tree<T> {
    private Node<T> root;

    public BST() {
        this.root = null;
    }

    /**
     * Obtiene la raíz del árbol.
     * @return la raíz del árbol.
     */
    public Node<T> getRoot() {
        return root;
    }

    /**
     * Establece la raíz del árbol.
     * @param root la nueva raíz del árbol.
     */
    public void setRoot(Node<T> root) {
        this.root = root;
    }

    /**
     * Calcula la altura del árbol.
     * @param tree el nodo raíz del árbol.
     * @return la altura del árbol.
     */
    // @Override
    // public int height(Node<T> tree) {
    //     if (tree == null) return 0;

    //     return 1 + Math.max(height(tree.getLeft()), height(tree.getRight()));
    // }

    /**
     * Calcula la altura del árbol revisando si es una hoja.
     * @param tree el nodo raíz del árbol.
     * @return la altura del árbol.
     */
    @Override
    public int height(Node<T> tree) {
        if (tree == null) return 0;
        if (tree.getLeft() == null && tree.getRight() == null) return 1;

        return 1 + Math.max(height(tree.getLeft()), height(tree.getRight()));
    }

    /**
     * Calcula el tamaño del árbol.
     * @param tree el nodo raíz del árbol.
     * @return el tamaño del árbol.
     */
    @Override
    public int size(Node<T> tree) {
        if (tree == null) return 0;

        return 1 + size(tree.getLeft()) + size(tree.getRight());
    }

    /**
     * Busca un nodo con la clave especificada.
     * @param key la clave a buscar.
     * @param tree el nodo raíz del árbol.
     * @return el nodo con la clave especificada, o el nodo donde debería estar.
     */
    @Override
    public Node<T> find(T key, Node<T> tree) {
        if (tree == null) return null;

        if (tree.getKey().compareTo(key) == 0) return tree;

        else if (tree.getKey().compareTo(key) > 0) {
            if (tree.getLeft() != null) 
                return find(key, tree.getLeft());
            else 
                return tree;
        }

        else if (tree.getKey().compareTo(key) < 0) {
            if (tree.getRight() != null) 
                return find(key, tree.getRight());
            else 
                return tree;
        }

        return null; // Esto nunca debería ocurrir
    }

    /**
     * Encuentra el siguiente nodo en el recorrido en orden.
     * @param node el nodo actual.
     * @return el siguiente nodo en el recorrido en orden.
     */
    @Override
    public Node<T> next(Node<T> node) {
        if (node.getRight() != null) 
            return leftDescendant(node.getRight());

        return rightAncestor(node);
    }

    /**
     * Encuentra el descendiente más a la izquierda.
     * @param node el nodo actual.
     * @return el descendiente más a la izquierda.
     */
    @Override
    public Node<T> leftDescendant(Node<T> node) {
        if (node.getLeft() == null) 
            return node;

        return leftDescendant(node.getLeft());
    }

    /**
     * Encuentra el ancestro más a la derecha.
     * @param node el nodo actual.
     * @return el ancestro más a la derecha.
     */
    @Override
    public Node<T> rightAncestor(Node<T> node) {
        if (node.getParent() == null || node.getKey().compareTo(node.getParent().getKey()) < 0) 
            return node.getParent();

        return rightAncestor(node.getParent());
    }

    /**
     * Realiza una búsqueda por rango.
     * @param lower el límite inferior del rango.
     * @param upper el límite superior del rango.
     * @return una lista de nodos dentro del rango especificado.
     */
    @Override
    public LinkedList<Node<T>> rangeSearch(T lower, T upper) {
        LinkedList<Node<T>> list = new LinkedList<>();
        Node<T> node = find(lower, this.root);

        while (node.getKey().compareTo(upper) <= 0) {
            if (node.getKey().compareTo(lower) >= 0) 
                list.add(node);

            node = next(node);
        }
        
        return list;
    }

    /**
     * Inserta una nueva clave en el árbol.
     * @param key la clave a insertar.
     */
    @Override
    public void insert(T key) {
        Node<T> parent = find(key, this.root);
        Node<T> newNode = new Node<>(key, parent);

        if (parent == null) {
            this.root = newNode;
            return;
        }
        
        if (parent.getKey().compareTo(key) < 0) 
            parent.setRight(newNode);
        else 
            parent.setLeft(newNode);

        newNode.setParent(parent);
    }

    /**
     * Elimina un nodo del árbol.
     * @param node el nodo a eliminar.
     */
    @Override
    public void delete(Node<T> node) {
        if (node.getRight() == null) 
            replaceNode(node, node.getLeft());
        else {
            Node<T> successor = next(node);

            if (successor != node.getRight()) {
                replaceNode(successor, successor.getRight());
                successor.setRight(node.getRight());
                node.getRight().setParent(successor);
            }

            replaceNode(node, successor);
            successor.setLeft(node.getLeft());

            if (node.getLeft() != null) 
                node.getLeft().setParent(successor);
        }
    }

    /**
     * Reemplaza un nodo por otro.
     * @param oldNode el nodo a reemplazar.
     * @param newNode el nuevo nodo.
     */
    private void replaceNode(Node<T> oldNode, Node<T> newNode) {
        if (oldNode.getParent() == null) {
            setRoot(newNode);
        } else {
            if (oldNode == oldNode.getParent().getLeft()) {
                oldNode.getParent().setLeft(newNode);
            } else {
                oldNode.getParent().setRight(newNode);
            }
        }
        if (newNode != null) {
            newNode.setParent(oldNode.getParent());
        }
    }

    /**
     * Convierte el árbol a una cadena de texto.
     * @return una representación en cadena del árbol.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringHelper(root, sb, "", "");
        return sb.toString();
    }

    /**
     * Método auxiliar para construir la representación en cadena del árbol.
     * @param node el nodo actual.
     * @param sb el StringBuilder para construir la cadena.
     * @param prefix el prefijo para el nodo actual.
     * @param childrenPrefix el prefijo para los hijos del nodo actual.
     */
    private void toStringHelper(Node<T> node, StringBuilder sb, String prefix, String childrenPrefix) {
        if (node != null) {
            sb.append(prefix);
            sb.append(node.getKey());
            sb.append("\n");
            toStringHelper(node.getRight(), sb, childrenPrefix + "├── ", childrenPrefix + "│   ");
            toStringHelper(node.getLeft(), sb, childrenPrefix + "└── ", childrenPrefix + "    ");
        }
    }
}