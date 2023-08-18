package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
  @Id @GeneratedValue
  @Column(name = "order_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id") //연관관계의 주인
  private Member member;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderitem = new ArrayList<>();

  private Delivery delivery;

  //private Date date; 밑에거 쓰면됌
  private LocalDateTime orderDate;

  private OrderStatus status; //주문상태 [ORDER, CANCEL]


}
