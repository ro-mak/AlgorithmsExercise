import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

public class TwoWayRelatedList<T> implements Iterable<T> {
    @Override
    public TwoWayIterator iterator() {
        return new TwoWayIterator();
    }

    public class TwoWayIterator implements Iterator<T> {
        private TwoWayNode<T> pointer = new TwoWayNode<>(null);

        public TwoWayIterator() {
            pointer.next = head;
            pointer.previous = null;
        }

        @Override
        public boolean hasNext() {
            return pointer.next != null && pointer.next.element != null;
        }

        public boolean hasPrevious() {
            return pointer.previous != null && pointer.previous.element != null;
        }

        @Override
        public T next() {
            T element;
            element = pointer.next.element;
            TwoWayNode<T> currentNode = pointer.next;
            pointer.next = pointer.next.next;
            if (pointer.next != null) {
                pointer.previous = pointer.next.previous;
            } else {
                pointer.previous = currentNode;
            }
            return element;
        }

        public T previous() {
            T element;
            element = pointer.previous.element;
            TwoWayNode<T> currentNode = pointer.previous;
            pointer.previous = pointer.previous.previous;
            if (pointer.next != null) {
                pointer.next = pointer.next.next;
            } else {
                pointer.next = currentNode;
            }
            return element;
        }

        public T getNext() {
            if (pointer.next != null) return pointer.next.element;
            return null;
        }

        public T getPrevious() {
            if (pointer.previous != null) return pointer.previous.element;
            return null;
        }

        public T removeNext() {
            T element = pointer.next.element;
            pointer.next = pointer.next.next;
            if (pointer.previous != null && pointer.previous.previous != null)
                pointer.previous = pointer.previous.previous;
            return delete(element);
        }

        public T removePrevious() {
            T element = pointer.previous.element;
            pointer.previous = pointer.previous.previous;
            if (pointer.next != null && pointer.next.next != null) pointer.next = pointer.next.next;
            return delete(element);
        }

        public void insertNext(T element) {
            TwoWayNode<T> elementNode = new TwoWayNode<>(element);
            if (pointer.next != null) {
                elementNode.next = pointer.next;
                pointer.next.previous = elementNode;
                if (pointer.previous != null) pointer.previous.next = elementNode;
                pointer.next = elementNode;
            } else {
                pointer.next = elementNode;
            }
            elementNode.previous = pointer.previous;
        }

        public void insertPrevious(T element) {
            TwoWayNode<T> elementNode = new TwoWayNode<>(element);
            if (pointer.previous != null) {
                elementNode.previous = pointer.previous;
                pointer.previous.next = elementNode;
                if (pointer.next != null) pointer.next.previous = elementNode;
                pointer.previous = elementNode;
            } else {
                pointer.previous = elementNode;
            }
            elementNode.next = pointer.previous;
        }
    }

    private class TwoWayNode<T> {
        TwoWayNode<T> previous;
        TwoWayNode<T> next;
        T element;

        public TwoWayNode(T element) {
            this.element = element;
        }

        @Override
        public String toString() {
            return element.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TwoWayNode)) return false;
            TwoWayNode<T> node = (TwoWayNode<T>) o;
            return Objects.equals(element, node.element);
        }
    }

    private TwoWayNode<T> head;

    public TwoWayRelatedList() {
        head = null;
    }

    public void insert(T element) {
        TwoWayNode<T> elementNode = new TwoWayNode<>(element);
        if (head != null) head.previous = elementNode;
        elementNode.next = head; // if (head == null) n.next = null;
        head = elementNode;
    }

    public T remove() {
        if (isEmpty())
            return null;
        T temp = head.element;
        head = head.next;
        head.previous = null;
        return temp;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public boolean contains(T element) {
        TwoWayNode<T> elementNode = new TwoWayNode<>(element);
        TwoWayNode<T> currentNode = head;
        while (!currentNode.equals(elementNode)) {
            if (currentNode.next == null)
                return false;
            else
                currentNode = currentNode.next;
        }
        return true;
    }

    public T delete(T element) {
        TwoWayNode<T> currentNode = head;
        TwoWayNode<T> previousNode = head;
        while (!currentNode.element.equals(element)) {
            if (currentNode.next == null)
                return null;
            else {
                previousNode = currentNode;
                currentNode = currentNode.next;
            }
        }
        if (currentNode == head) {
            head.next.previous = head.previous;
            head = head.next;
        } else {
            previousNode.next = currentNode.next;
            if (currentNode.next != null) currentNode.next.previous = previousNode;
        }

        return element;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        TwoWayNode<T> current = head;
        StringBuilder sb = new StringBuilder("[");
        while (current != null) {
            sb.append(current);
            current = current.next;
            sb.append((current == null) ? "]" : ", ");
        }
        return sb.toString();
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public Spliterator<T> spliterator() {
        return null;
    }
}
