package prob04;

public class Depart extends Employee{
	private String dept;
	
	public Depart(String name, int salary, String dept) {
		this.setName(name);
		this.setSalary(salary);
		this.dept = dept;
	}

	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	
	public void getInformation() {
		System.out.println( "이름:" + getName() + " 연봉:" + getSalary() + " 부서: " + dept );
	}
	
}
