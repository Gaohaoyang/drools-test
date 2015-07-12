package com.sample;

public class Message {
	
/**
 * test
 * a1, a2->a3
 * a3->a4
 */
	private boolean a1;
	private boolean a2;
	private boolean a3;
	private boolean a4;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "a1-"+a1+", a2-"+a2+", a3-"+a3+", a4-"+a4;
	}
	
	public boolean isA1() {
		return a1;
	}
	public void setA1(boolean a1) {
		this.a1 = a1;
	}
	public boolean isA2() {
		return a2;
	}
	public void setA2(boolean a2) {
		this.a2 = a2;
	}
	public boolean isA3() {
		return a3;
	}
	public void setA3(boolean a3) {
		this.a3 = a3;
	}
	public boolean isA4() {
		return a4;
	}
	public void setA4(boolean a4) {
		this.a4 = a4;
	}
	
}
