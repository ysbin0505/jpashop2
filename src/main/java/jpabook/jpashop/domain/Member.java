package jpabook.jpashop.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
public class Member {
  @Id @GeneratedValue
  @Column(name = "member_id")
  private Long id;    //id
  @NotEmpty
  private String name;    //회원명

  private String company;

  @Embedded
  private Address address;    //주소

  @JsonIgnore //주문정보를 빼줌
  @OneToMany(mappedBy = "member")   //연관관계의 주인이 아니다.
  private List<Order> orders = new ArrayList<>();
}