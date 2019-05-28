/**
 * 
 */
package com.cream.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.cream.core.CreamVersion;
import com.cream.core.base.entity.DataEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 操作表
 * 
 * @author cream
 *
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
public class SysOperation extends DataEntity {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    /**
     * 操作名称
     */
    @Column(nullable = false, length = 60)
    private String operationName;

    /**
     * 操作描述
     */
    @Column(length = 255)
    private String operationDescritpion;
}
