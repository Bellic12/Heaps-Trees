package Interfaces;

import Nodes.Node;
import java.util.LinkedList;

public interface Tree<T extends Comparable<T>> {
    int height(Node<T> tree);
    int size(Node<T> tree);

    Node<T> find(T key, Node<T> tree);
    Node<T> next(Node<T> node);
    Node<T> leftDescendant(Node<T> node);
    Node<T> rightAncestor(Node<T> node);
    LinkedList<Node<T>> rangeSearch(T lower, T upper);
    void insert(T key);
    void delete(Node<T> node);

    String toString();
}
