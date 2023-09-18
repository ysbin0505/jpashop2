# jpashop2
활용 2



 /*어떤 식으로 update가 발생하는가?
 1. postman에서 send 버튼을 누르면 ex)http://localhost:8070/api/v1/counsels/43  send  
 @PutMappling {id값 받은} 호출
 2. @PathVariable과 @RequestBody @Valid를 통해 updateMemberV2에 각각 id, request(UpdateMemberRequest DTO)에 저장
    -> UpdateMemberRequest에는 name과 company가 있음
 3. update 호출 -> @Transactional이 붙어있는 영속성컨텐츠인 update에는 id와 name, company를 변수로 받아
 memberRepository의 id와 비교하여 set을 통해 커밋되는 시점에서 변경감지 업데이트!!
 4.  Member findMember = memberService.findOne(id);  이것은 잘 변경되었는지 확인하는 것
 5. 그 다음 UpdateMemberResponse DTO를 통해 반환

  */
