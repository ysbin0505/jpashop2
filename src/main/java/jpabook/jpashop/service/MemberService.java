package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //기본 read only false로 지정
//@AllArgsConstructor -> 밑에있는 생성자를 만들어줌
@RequiredArgsConstructor //-> final이 있는 필드만을 가지고 생성자를 만들어줌
public class MemberService {

  private final MemberRepository memberRepository;

  /*@Autowired  //생성자 injection -> 한번하면 수정불가 + 생성자 하나만 있으면 AUTOWIRED해줌
  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }*/
  
  /*@Autowired
  public void setMemberRepository(MemberRepository memberRepository) {    setter injection 방식(Test에 좋음)
    this.memberRepository = memberRepository;
  }*/  

  /*
    @@회원가입
     */
  @Transactional //변경 + readonly false
  public Long join(Member member){
    validateDuplicateMember(member);    //중복 회원 검증
    memberRepository.save(member);
    return member.getId();   //em.persist 아직 db에 들어간 시점이 아니라도 값을 넣어줌
  }

  private void validateDuplicateMember(Member member) {
    //EXCEPTION
    List<Member> findMembers = memberRepository.findByName(member.getName()); //멀티쓰레드 예방으로 DB에서 member의 name을 unique로 지정
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

  @Transactional
  public void update(Long id, String name, String company) {
    Member member = memberRepository.findOne(id); //레포지토리에서 id를 찾아옴
    member.setName(name); //그 이름을 바꿈(@@@@변경감지@@@@)
    member.setCompany(company);
  }
}
