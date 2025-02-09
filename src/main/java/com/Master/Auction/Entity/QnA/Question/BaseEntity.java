package com.Master.Auction.Entity.QnA.Question;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.Getter;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdTime;

     @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedTime;
}
