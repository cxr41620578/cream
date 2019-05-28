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

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author cream
 *
 */
@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
public class BaseEntity extends Entity {
    
    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
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
}
