package socketcalculator;

import java.io.Serializable;

/**
 * This class is used to transfer data over socket, hence it should be
 * serializable.
 */
class Operation implements Serializable {
    private final OperationType operation;
    private final int number1;
    private final int number2;
    private float result;

    Operation(OperationType operation, int number1, int number2) {
        super();
        this.operation = operation;
        this.number1 = number1;
        this.number2 = number2;
    }

    float getResult() {
        return result;
    }

    void setResult(float result) {
        this.result = result;
    }

    OperationType getOperationType() {
        return operation;
    }

    int getNumber1() {
        return number1;
    }

    int getNumber2() {
        return number2;
    }

    enum OperationType {
        ADD, MULTIPLY, DIVIDE, SUBTRACT
    }
}
