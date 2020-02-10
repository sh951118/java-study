package prob5;

public class MyStack {
	private int top;
	private int size;
	private String[] buffer;
	private String[] buf;
	private int i;
	public MyStack(int size) {
		top = -1;
		this.size = size;
		this.buffer = new String[this.size];
		this.buf = new String[this.size + 2];
		buffer = buf;
	}

	public void push(String item){
		
		if(top == size - 1) {
			for(i = 0; i < size; i++) {
				buf[i] = buffer[i];
			}
			buf[++top] = item;
		}
		else {
			buffer[++top] = item;
		}
	}

	public boolean isEmpty() {
		if(top == -1) {
			return true;
		}
		else {
			return false;
		}
	}

	public String pop() throws prob5.MyStackException{
		if(isEmpty()) {
			throw new MyStackException();
		}
		else {
			return buf[top--];
		}
	}
}