package prob3;

public class Sparrow extends Bird{

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected void fly() {
		System.out.println("참새(" + name + ")가 날아다닙니다.");
	}

	@Override
	protected void sing() {
		System.out.println("참새(" + name + ")가 소리내어 웁니다.");
	}
	
	public String toString() {
		return "참새의 이름은 " + getName() + " 입니다.";
	}
}
