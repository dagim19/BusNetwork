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

    public Node<T> find(T data){
        Node<T> current = head.next;
        while(current != tail){
            if(current.data.equals(data)) return current;
            current = current.next;
        }
        return null;
    }

    public void insertBefore(Node<T> before, Node<T> after) {
        Node<T> newNode = new Node<T>(before.data, after, after.prev);
        after.prev.next = newNode;
        after.prev = newNode;
        size++;
    }

    public void insertAfter(Node<T> after, Node<T> before) {
        Node<T> newNode = new Node<T>(before.data, after.next, after);
        after.next.prev = newNode;
        after.next = newNode;
        size++;
    }

   public int removeBefore(Node<T> before) {
        if(before.prev == head) return -1;
        Node<T> current = before.prev;
        before.prev = current.prev;
        current.prev.next = before;
        size--;
        return 0;
    }

    public int removeAfter(Node<T> after) {
        if(after.next == tail) return -1;
        Node<T> current = after.next;
        after.next = current.next;
        current.next.prev = after;
        size--;
        return 0;
    }

    public void insertRangeBefore(Node<T> before, DList<T> list) {
        if(list.size == 0) return;
        Node<T> current = list.head.next;
        while(current != list.tail){
            Node<T> newNode = new Node<T>(current.data, before, before.prev);
            before.prev.next = newNode;
            before.prev = newNode;
            size++;
            current = current.next;
        }
    }

    public void insertRangeAfter(Node<T> after, DList<T> list) {
        if(list.size == 0) return;
        Node<T> current = list.tail.prev;
        while(current != list.head){
            Node<T> newNode = new Node<T>(current.data, after.next, after);
            after.next.prev = newNode;
            after.next = newNode;
            size++;
            current = current.prev;
        }
    }

    public void removeRange(Node<T> start, Node<T> end) {
        if(start == end) return;
        Node<T> current = start;
        while(current != end){
            Node<T> temp = current.next;
            current.prev.next = current.next;
            current.next.prev = current.prev;
            size--;
            current = temp;
        }
    }

    public DList<T> getSublist(Node<T> rangeFirst, Node<T> rangeLast) {
        DList<T> list = new DList<T>();
        Node<T> current = rangeFirst;
        while(current != rangeLast){
            list.addLast(current.data);
            current = current.next;
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