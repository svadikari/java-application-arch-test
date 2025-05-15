package com.shyam.archunit.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public record Product(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id,

    @NotEmpty String name,

    @NotNull String description,

    @NotNull Double price,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonIgnore
    LocalDateTime createdDate,

    @LastModifiedDate
    @Column(insertable = false)
    @JsonIgnore
    LocalDateTime lastModifiedDate,

    @CreatedBy
    @NotBlank
    @Column(nullable = false, updatable = false) String createdBy,

    @LastModifiedBy
    @Column(insertable = false)
    @JsonIgnore
    String lastModifiedBy
) {

}
