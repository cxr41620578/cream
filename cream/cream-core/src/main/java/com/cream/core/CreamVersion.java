/**
 * 
 */
package com.cream.core;

/**
 * @author cream
 *
 */
public final class CreamVersion {
    
    private CreamVersion() {}

    public static final long SERIAL_VERSION_UID = 1L;
    
    public static String getVersion() {
        Package pkg = CreamVersion.class.getPackage();
        return (pkg != null ? pkg.getImplementationVersion() : null);
    }
}
