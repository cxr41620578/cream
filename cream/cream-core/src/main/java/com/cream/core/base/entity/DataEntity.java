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

/**
 * @author cream
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class DataEntity extends BaseEntity {

    private static final long serialVersionUID = CreamVersion.SERIAL_VERSION_UID;

    // Constructor

    public DataEntity() {}

    protected DataEntity(DataEntityBuilder<?, ?> b) {
        super(b);
        this.createBy = b.createBy;
        this.createDate = b.createDate;
        this.lastModifiedBy = b.lastModifiedBy;
        this.lastModifiedDate = b.lastModifiedDate;
    }
    
    // field

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

    /*
     * Getter and setter
     */

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    // Builder

    public static DataEntityBuilder<?, ?> builder() {
        return new DataEntityBuilderImpl();
    }

    public static abstract class DataEntityBuilder<C extends DataEntity, B extends DataEntityBuilder<C, B>>
            extends BaseEntityBuilder<C, B> {
        private Long createBy;

        private Date createDate;

        private Long lastModifiedBy;

        private Date lastModifiedDate;

        public B createBy(Long createBy) {
            this.createBy = createBy;
            return self();
        }

        public B createDate(Date createDate) {
            this.createDate = createDate;
            return self();
        }

        public B lastModifiedBy(Long lastModifiedBy) {
            this.lastModifiedBy = lastModifiedBy;
            return self();
        }

        public B lastModifiedDate(Date lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
            return self();
        }

        @Override
        public String toString() {
            return "DataEntity.DataEntityBuilder(super=" + super.toString() + ", createBy=" + this.createBy
                    + ", createDate=" + this.createDate + ", lastModifiedBy=" + this.lastModifiedBy
                    + ", lastModifiedDate=" + this.lastModifiedDate + ")";
        }

        public abstract C build();

        protected abstract B self();
    }

    private static final class DataEntityBuilderImpl extends DataEntityBuilder<DataEntity, DataEntityBuilderImpl> {

        private DataEntityBuilderImpl() {
        }

        @Override
        protected DataEntityBuilderImpl self() {
            return this;
        }

        @Override
        public DataEntity build() {
            return new DataEntity(this);
        }
    }
}
