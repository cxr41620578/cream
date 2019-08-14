/**
 * 
 */
package com.cream.security.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.cream.core.CreamVersion;
import com.cream.core.base.entity.DataEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 资源表
 * 
 * @author cream
 *
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
public class SysResource extends DataEntity {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    /**
     * 资源名称
     */
    @Column(nullable = false, length = 60)
    private String resourceName;
    
    /**
     * 资源描述
     */
    @Column(length = 255)
    private String resourceDescritpion;

    /**
     * 父资源
     */
    @ManyToOne
    @JoinColumn(name = "parentId")
    private SysResource parentResource;

    /**
     * 子资源
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Set<SysResource> childrenResource;
}
