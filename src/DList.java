import java.util.Iterator;

class Node<T>{
    T data;
    Node next;
    Node prev;
    public Node(T data, Node next, Node prev){
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}

public class DList<T> implements Iterable<T>{
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public DList(){
        head = new Node<T>(null, null, null);
        tail = new Node<T>(null, null, head);
        head.next = tail;
        size = 0;
    }
    public void addFirst(T data){
        Node<T> newNode = new Node<T>(data, head.next, head);
        head.next.prev = newNode;
        head.next = newNode;
        size++;
    }

    public void addLast(T data){
        Node<T> newNode = new Node<T>(data, tail, tail.prev);
        tail.prev.next = newNode;
        tail.prev = newNode;
        size++;
    }

    public T removeFirst(){
        if(size == 0) return null;
        Node<T> first = head.next;
        head.next = first.next;
        first.next.prev = head;
        size--;
        return first.data;
    }

    public T removeLast(){
        if(size == 0) return null;
        Node<T> last = tail.prev;
        tail.prev = last.prev;
        last.prev.next = tail;
        size--;
        return last.data;
    }

    public T getFirst(){
        if(size == 0) return null;
        return (T) head.next.data;
    }

    public T getLast(){
        if(size == 0) return null;
        return (T) tail.prev.data;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public T get(int index){
        if(index < 0 || index >= size) return null;
        Node<T> current = head.next;
        for(int i = 0; i < index; i++){
            current = current.next;
        }
        return current.data;
    }

    public Node<T> find(T data){
        Node<T> current = head.next;
        while(current != tail){
            if(current.data.equals(data)) return current;
            current = current.next;
        }
        return null;
    }

    public boolean add(T item) {
        addLast(item);
        return true;
    }

    public boolean remove(T item) {
        Node<T> current = head.next;
        while(current != tail){
            if(current.data.equals(item)){
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public DList<T> backwards() {
        DList<T> list = new DList<T>();
        Node<T> current = tail.prev;
        while(current != head){
            list.addFirst(current.data);
            current = current.prev;
        }
        return list;
    }


    public Iterator<T> iterator(){
        return new DListIterator();
    }
    private class DListIterator implements Iterator<T>{
        private Node<T> current = head.next;
        public boolean hasNext(){
            return current != tail;
        }
        public T next(){
            T data = current.data;
            current = current.next;
            return data;
        }
    }
}