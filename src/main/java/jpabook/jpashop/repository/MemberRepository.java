package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
//@PersistenceContext
public class MemberRepository {

  private final EntityManager em; //스프링이 EntityManager를 만들어서 인젝션해줌(주입)


  public void save(Member member){    //jpa가 저장하는 로직
    em.persist(member);
  }

  public Member findOne(Long id){
    return em.find(Member.class, id);
  }

  public List<Member> findAll(){  //조회된 결과를 반환
    List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
    return result;
  }   //sql은 테이블에 대해 쿼리를 하지만 여기서는 객체에 대해 쿼리를 만듦

  public List<Member> findByName(String name){
    return em.createQuery("select m from Member m where m.name = :name", Member.class)
        .setParameter("name",name).getResultList();
  }
  
}
