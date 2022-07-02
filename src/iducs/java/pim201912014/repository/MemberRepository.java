package iducs.java.pim201912014.repository;

import java.util.List;

public interface MemberRepository<T> {
    // 구현의 방향을 제시, 외부 사용법을 결정
    boolean create(T member); // 등록
    T readById(T member);  // 정보조회 - id 기준
    T readByEmail(T member); // 정보조회 - email 기준
    List<T> readList(); // 목록 조회
    boolean update(T member); // 수정
    boolean delete(T member); // 탈퇴

    List<T> getMemberList();
    void setMemberList(List<T> memberList);
    List<T> readListByPhone(T member);
    List<T> readListByName(String order);
    List<T> readListByPerPage(int page, int perPage);

}
