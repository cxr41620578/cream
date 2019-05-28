/**
 * 
 */
package com.cream.core.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
public class DataEntity extends BaseEntity {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
    
    @CreatedBy
    @Column(nullable = false)
    protected Long createBy;
    
    @CreatedDate
    @Column(nullable = false)
    protected Date createDate;
    
    @LastModifiedBy
    @Column
    protected Long lastModifiedBy;
    
    @LastModifiedDate
    @Column
    protected Date lastModifiedDate;
}
