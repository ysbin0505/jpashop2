package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

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

  /*
 1. postman에서 send 버튼을 누르면 ex)http://localhost:8070/api/v1/counsels/43  send  
 @PutMappling {id값 받은} 호출
 2. @PathVariable과 @RequestBody @Valid를 통해 updateMemberV2에 각각 id, request(UpdateMemberRequest DTO)에 저장
    -> UpdateMemberRequest에는 name과 company가 있음
 3. update 호출 -> @Transactional이 붙어있는 영속성컨텐츠인 update에는 id와 name, company를 변수로 받아
 memberRepository의 id와 비교하여 set을 통해 커밋되는 시점에서 변경감지 업데이트!!
 4.  Member findMember = memberService.findOne(id);  이것은 잘 변경되었는지 확인하는 것
 5. 그 다음 UpdateMemberResponse DTO를 통해 반환

  */


  @PutMapping("/api/v2/members/{id}") //update용 request DTO와 update용 응답 DTO를 별도로 만듦
  public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                             @RequestBody @Valid UpdateMemberRequest request){
    memberService.update(id,request.getName(), request.getCompany());
    Member findMember = memberService.findOne(id);  //findOne매서드 이용해서 id로 찾음
    return new UpdateMemberResponse(findMember.getId(), findMember.getName(), findMember.getCompany());
  }

  @Data
  static class UpdateMemberRequest{
    private String name;
    private String company;
  }

  @Data
  @AllArgsConstructor
  static class  UpdateMemberResponse{
    private Long id;
    private String name;
    private String company;
  }

  @GetMapping("/api/v1/members") //회원정보만 원하는데 전부 다 출력됌, @JsonIgnore로 몇개는 해결가능하나 너무 광범위함
                                    //엔티티가 변경되어 API스펙이 변함 걍 쓰지말자!!!!!!!
  public List<Member> membersV1(){
    return memberService.findMembers();
  }

  @GetMapping("/api/v2/members")
  public Result membersV2(){
    List<Member> findMembers = memberService.findMembers();
    List<MemberDto> collect = findMembers.stream()
        .map(m -> new MemberDto(m.getName()))
        .collect(Collectors.toList());

    return new Result(collect);
  }

  @Data
  @AllArgsConstructor
  static class Result<T>{
    private T data;
  }

  @Data
  @AllArgsConstructor
  static class MemberDto{
    private String name;
  }

}
