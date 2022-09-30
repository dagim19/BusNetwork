public class DList<T>{
    private class Node{
        private T data;
        private Node next;
        private Node prev;
        public Node(T data, Node next, Node prev){
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node front;
    private Node back;

    public DList(){
        front = null;
        back = null;
    }

    public boolean isEmpty(){
        return front == null;
    }

    public void insertFront(T data){
        if (isEmpty()){
            front = new Node(data, null, null);
            back = front;
        }
        else{
            front = new Node(data, front, null);
            front.next.prev = front;
        }
    }

    public void insertBack(T data){
        if (isEmpty()){
            back = new Node(data, null, null);
            front = back;
        }
        else{
            back = new Node(data, null, back);
            back.prev.next = back;
        }
    }

    public boolean removeFront(){
        if (isEmpty()){
            return false;
        }
        else if (front == back){
            front = null;
            back = null;
            return true;
        }
        else{
            front = front.next;
            front.prev = null;
            return true;
        }
    }

    public boolean removeBack() {
        if (isEmpty()) {
            return false;
        }
        else if (front == back) {
            front = null;
            back = null;
            return true;
        }
        else {
            back = back.prev;
            back.next = null;
            return true;
        }
    }

    public void insertAfter(T data, T key){
        Node temp = front;
        while (temp != null && !temp.data.equals(key)){
            temp = temp.next;
        }
        if (temp == null){
            return;
        }
        else if (temp == back){
            insertBack(data);
        }
        else{
            Node newNode = new Node(data, temp.next, temp);
            temp.next.prev = newNode;
            temp.next = newNode;
        }
    }

    public void insertBefore(T data, T key){
        Node temp = front;
        while (temp != null && !temp.data.equals(key)){
            temp = temp.next;
        }
        if (temp == null){
            return;
        }
        else if (temp == front){
            insertFront(data);
        }
        else{
            Node newNode = new Node(data, temp, temp.prev);
            temp.prev.next = newNode;
            temp.prev = newNode;
        }
    }

    public boolean removeBefore(T key){
        Node temp = front;
        while (temp != null && !temp.data.equals(key)){
            temp = temp.next;
        }
        if (temp == null || temp == front){
            return false;
        }
        else if (temp.prev == front){
            removeFront();
            return true;
        }
        else{
            temp.prev.prev.next = temp;
            temp.prev = temp.prev.prev;
            return true;
        }
    }

    public boolean removeAfter(T key) {
        Node temp = front;
        while (temp != null && !temp.data.equals(key)) {
            temp = temp.next;
        }
        if (temp == null || temp == back) {
            return false;
        }
        else if (temp.next == back) {
            removeBack();
            return true;
        }
        else {
            temp.next.next.prev = temp;
            temp.next = temp.next.next;
            return true;
        }
    }

    // find function
    public boolean find(T key){
        Node temp = front;
        while (temp != null && !temp.data.equals(key)){
            temp = temp.next;
        }
        if (temp == null){
            return false;
        }
        return true;
    }

    public void insertRangeBefore(T data, T key1, T key2){
        Node temp = front;
        while (temp != null && !temp.data.equals(key1)){
            temp = temp.next;
        }
        if (temp == null){
            return;
        }
        while (temp != null && !temp.data.equals(key2)){
            insertBefore(data, temp.data);
            temp = temp.next;
        }
    }


}
