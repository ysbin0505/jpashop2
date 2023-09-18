package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

//@Controller @ResponseBody
@RestController // @Controller와 @ResponseBody 둘 다 가짐
@RequiredArgsConstructor
public class MemberApiController {
  private final MemberService memberService;

  @PostMapping("/api/v1/members")   //name을 username으로 바꾸면 API스펙 자체가 변경되어버림(+엔티티 절대 노출하지마 그냥 이거 쓰지마)
  public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){  //@Valid가 Member에서 api에 대한 검증을 함
    Long id = memberService.join(member);                                       //기본키나 notempty같은 것들
    return new CreateMemberResponse(id);
  }

  @PostMapping("/api/v2/members") //name을 username으로 바꾸면 오류남 -> DTO로 받으면 더 안정적! 이거 추천
  public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
    Member member = new Member();
    member.setName(request.getName());
    member.setCompany(request.getCompany());

    Long id = memberService.join(member);
    return new CreateMemberResponse(id);
  }

  @Data //v2
  static class CreateMemberRequest{
    @NotEmpty
    private String name;

    private String company;
  }

  @Data //v1
  static class CreateMemberResponse{
    private Long id;

    public CreateMemberResponse(Long id) {
      this.id = id;
    }
  }
}
