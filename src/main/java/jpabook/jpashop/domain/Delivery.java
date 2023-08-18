package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
  @Id @GeneratedValue
  @Column(name = "delivery_id")
  private Long id;

  @OneToOne(mappedBy = "delivery")
  private Order order;

  @Embedded
  private Address address;

  @Enumerated(EnumType.STRING)  //ORDINARY 절대 쓰면 안됌 -> READY, XXX , COMP 이런식으로 밀릴수도있음
  private DeliveryStatus status; //READY, COMP


}
