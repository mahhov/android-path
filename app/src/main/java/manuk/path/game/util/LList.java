package manuk.path.game.util;

import java.util.Iterator;

public class LList<T> implements Iterable<T> {
	private Node head, tail;
	
	public LList() {
		head = tail = new Node(null, null, null);
	}
	
	public Node addHead(T elem) {
		head = new Node(elem, head, null);
		head.prev.next = head;
		return head;
	}
	
	public T removeTail() {
		if (tail.next == null)
			return null;
		tail = tail.next;
		tail.prev = null;
		return tail.elem;
	}
	
	public T remove(Node node) {
		if (node.next != null)
			node.next.prev = node.prev;
		if (node.prev != null)
			node.prev.next = node.next;
		if (node == head)
			head = head.prev;
		return node.elem;
	}
	
	public boolean isEmpty() {
		return tail == head;
	}
	
	public Iterator<T> iterator() {
		return new LListElemIterator();
	}
	
	public Iterator<Node> nodeIterator() {
		return new LListNodeIterator();
	}
	
	public class Node {
		public T elem;
		private Node prev, next;
		
		private Node(T elem, Node prev, Node next) {
			this.elem = elem;
			this.prev = prev;
			this.next = next;
		}
	}
	
	private class LListElemIterator implements Iterator<T> {
		Node cur;
		
		LListElemIterator() {
			cur = tail;
		}
		
		public boolean hasNext() {
			return cur.next != null;
		}
		
		public T next() {
			cur = cur.next;
			return cur.elem;
		}
	}
	
	private class LListNodeIterator implements Iterator<Node> {
		Node cur;
		
		LListNodeIterator() {
			cur = tail;
		}
		
		public boolean hasNext() {
			return cur.next != null;
		}
		
		public Node next() {
			cur = cur.next;
			return cur;
		}
	}
}
