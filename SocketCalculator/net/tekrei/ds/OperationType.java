package net.tekrei.ds;

import java.io.Serializable;

/**
 * Available operations
 * @author tekrei
 * 
 */
public enum OperationType{
    
    ADD("ADD"), MULTIPLY("MULTIPLY"),DIVIDE("DIVIDE"),SUBTRACT("SUBTRACT");
    
    private String operationType;
    
    OperationType(String operationType){
        this.operationType = operationType;
    }
 }
