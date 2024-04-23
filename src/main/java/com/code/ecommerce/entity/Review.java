package com.code.ecommerce.entity;


import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Setter
@Getter
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Review extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    private Double rate;

    private String content;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "create_date")
    private Date createDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
