package net.tekrei.ds;

import java.io.Serializable;

/**
 * This class is used to transfer data over socket, hence it should be 
 * serializable.
 * 
 * @author tekrei
 * 
 */
public class Operation implements Serializable {
	private OperationType operation;
	private int number1;
	private int number2;
	private float result;

	public Operation(OperationType operation, int number1, int number2) {
		super();
		this.operation = operation;
		this.number1 = number1;
		this.number2 = number2;
	}

	public float getResult() {
		return result;
	}

	public void setResult(float result) {
		this.result = result;
	}

	public OperationType getOperationType() {
		return operation;
	}

	public void setOperationType(OperationType operation) {
		this.operation = operation;
	}

	public int getNumber1() {
		return number1;
	}

	public void setNumber1(int number1) {
		this.number1 = number1;
	}

	public int getNumber2() {
		return number2;
	}

	public void setNumber2(int number2) {
		this.number2 = number2;
	}
}
