package com.shyam.archunit.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  private Long id;

  @Column(nullable = false)
  @NotBlank
  private String status;

  @NotNull
  @OneToMany(mappedBy = "order")
  private List<OrderItems> orderItems;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  @JsonIgnore
  private LocalDateTime createdDate;

  @LastModifiedDate
  @Column(insertable = false)
  @JsonIgnore
  private LocalDateTime lastModifiedDate;

  @CreatedBy
  @NotBlank
  @Column(nullable = false, updatable = false)
  private String createdBy;

  @LastModifiedBy
  @Column(insertable = false)
  @JsonIgnore
  private String lastModifiedBy;
}

