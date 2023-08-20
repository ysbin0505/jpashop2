package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    validateDuplicateMember(member);    //중복 회원 검증
    memberRepository.save(member);
    return member.getId();   //em.persist 아직 db에 들어간 시점이 아니라도 값을 넣어줌
  }

  private void validateDuplicateMember(Member member) {
    //EXCEPTION
    List<Member> findMembers = memberRepository.findByName(member.getName());
    if (!findMembers.isEmpty()){
      throw new IllegalStateException("이미 존재하는 회원입니다.");   //member수를 카운트해서 0보다 크다 라고 하면 더 최적화 되긴함
    }
  }
  
  /*
  @@회원 조회
   */
  public List<Member> findMembers(){
    return memberRepository.findAll(); //전체 조회
  }

  public Member findOne(Long memberId){
    return memberRepository.findOne(memberId);
  }
}
