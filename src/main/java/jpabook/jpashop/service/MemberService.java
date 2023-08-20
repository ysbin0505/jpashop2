package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

  @Autowired
  MemberRepository memberRepository;

  /*
  @@회원가입
   */
  @Transactional //변경
  public Long join(Member member){
    
  }
  
  /*
  @@회원 전체 조회
   */
}
