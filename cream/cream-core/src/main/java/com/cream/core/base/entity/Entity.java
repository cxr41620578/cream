/**
 * 
 */
package com.cream.core.base.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

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
public class Entity implements Serializable {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;
}
