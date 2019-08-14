/**
 * 
 */
package com.cream.core.base.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cream.core.CreamVersion;

/**
 * @author v-chenxr04
 *
 */
@javax.persistence.Entity
@Table(name = "[dual]")
public final class Dual extends Entity {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
    
    // Constructor

    public Dual() {}

    protected Dual(String dummy) {
        this.dummy = dummy;
    }

    @Id
    @Column(length = 1)
    private String dummy;

    // Getter and setter
    
    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }
    
    // Builder
    
    public static Dual.DualBuilder builder() {
        return new Dual.DualBuilder();
    }

    public static class DualBuilder {
        private String dummy;
        
        private DualBuilder() {}
        
        public DualBuilder dummy(String dummy) {
            this.dummy = dummy;
            return this;
        }
        
        public Dual builder() {
            return new Dual(this.dummy);
        }
        
        @Override
        public String toString() {
            return "Dual.DualBuilder(dummy=" + this.dummy + ")";
        }
    }
}