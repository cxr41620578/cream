/**
 * 
 */
package com.cream.core.base.entity;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.cream.core.CreamVersion;

/**
 * @author cream
 *
 */
@MappedSuperclass
public class BaseEntity extends Entity {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
    
    // Constructor
    
    public BaseEntity() {}
    
    protected BaseEntity(BaseEntityBuilder<?, ?> b) {
        this.id = b.id;
    }
    
    // field
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    // equals and hashCode
    
    protected boolean canEqual(Object obj) {
        return obj instanceof BaseEntity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BaseEntity) {
            BaseEntity that = (BaseEntity) obj;
            return new EqualsBuilder().appendSuper(that.canEqual(this)).append(this.getId(), that.getId()).isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
    
    // Getter and setter
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    // Builder
    
    public static BaseEntityBuilder<?, ?> builder() {
        return new BaseEntityBuilderImpl();
    }
    
    public static abstract class BaseEntityBuilder<C extends BaseEntity, B extends BaseEntityBuilder<C, B>> {
        private Long id;
        
        public B id(Long id) {
            this.id = id;
            return self();
        }
        
        @Override
        public String toString() {
            return "BaseEntity.BaseEntityBuilder()";
        }

        public abstract C build();
        
        protected abstract B self();
    }
    
    private static final class BaseEntityBuilderImpl extends BaseEntityBuilder<BaseEntity, BaseEntityBuilderImpl> {
        
        private BaseEntityBuilderImpl() {}

        @Override
        protected BaseEntityBuilderImpl self() {
            return this;
        }

        @Override
        public BaseEntity build() {
            return new BaseEntity(this);
        }
    }
}
