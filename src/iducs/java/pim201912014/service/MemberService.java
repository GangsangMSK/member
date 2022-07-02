package iducs.java.pim201912014.service;

import java.util.List;

public interface MemberService<T> {
    // 외부적으로 구현 예정과 사용 방법(이름, 매개변수, 리턴)을 결정
    // Generics : 1. 컴파일 시점에 유형 확인으로 신뢰성 향상
    //            2. 여러 개의 클래스를 정의한 효과를 기대할 수 있음
    T login(String email, String pw);
    void logout();
    boolean postMember(T member); // 등록
    T getMember(T member); // 조회
    boolean putMember(T member); // 수정
    boolean deleteMember(T member); // 삭제, 탈퇴
    List<T> getMemberList(); // 목록조회 : 관리자
    List<T> findMemberByPhone(T member); // 전화번호로 검색
    // file 사용으로 필요한 연산
    void readFile();
    void saveFile();
    void applyUpdate();
    List<T> sortByName(String order);
    List<T> paginateByPerPage(int pageNo, int perPage);
}
