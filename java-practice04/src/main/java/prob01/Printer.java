package prob01;

public class Printer {
	private int i;
	private boolean b;
	private double d;
	private String s1;

	private int j;
	private int k;
	private int l;
	private int m;
	private int n;
	private int i1;
	private String s2;
	private StringBuffer s3;

	public void println(int i) {
		this.i = i;
		System.out.println(i);
	}

	public void println(boolean b) {
		this.b = b;
		System.out.println(b);
	}

	public void println(double d) {
		this.d = d;
		System.out.println(d);
	}

	public void println(String s1) {
		this.s1 = s1;
		System.out.println(s1);
	}

	public void println(int j, int k, int l, int m, int n, String s2, StringBuffer s3) {
		this.j = j;
		this.k = k;
		this.l = l;
		this.m = m;
		this.n = n;
		this.s2 = s2;
		this.s3 = s3;

		System.out.println(j + " " +k+ " " +l+ " " +m+ " " +n+ " " +s2+ " " +s3);
	}
}
