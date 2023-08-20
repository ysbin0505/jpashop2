package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;

@RunWith(SpringRunner.class)
@SpringBootTest //Spring boot 띄운 상태에서 테스트하려면 필요
@Transactional  //기본적으로 롤백을 해버림
public class MemberServiceTest {

  @Autowired MemberService memberService;
  @Autowired MemberRepository memberRepository;

  @Test
  //@Rollback(value = false)
  public void 회원가입() throws Exception{
    //given
   Member member = new Member();
   member.setName("Kim");

    //when
    Long savedId = memberService.join(member);

    //then
    Assert.assertEquals(member, memberRepository.findOne(savedId));
  }

  @Test(expected = IllegalStateException.class)
  public void 중복_회원_예외() throws Exception{
    //given
    Member member1 = new Member();
    member1.setName("Kim");

    Member member2 = new Member();
    member2.setName("Kim");

    //when
    memberService.join(member1);
    memberService.join(member2);
    /*
    memberService.join(member1);
    try {
      memberService.join(member2);  //예외가 발생해야한다.
    } catch (IllegalStateException e){
      return;
    } finally {

    }*/

    //then
    fail("예외가 발생해야 한다.");

  }

}