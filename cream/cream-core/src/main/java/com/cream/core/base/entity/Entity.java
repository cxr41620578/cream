/**
 * 
 */
package com.cream.core.base.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.cream.core.CreamVersion;

/**
 * @author cream
 *
 */
@MappedSuperclass
public class Entity implements Serializable {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
}
