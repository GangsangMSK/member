package iducs.java.pim201912014.controller;

import iducs.java.pim201912014.domain.Member;
import iducs.java.pim201912014.service.MemberService;
import iducs.java.pim201912014.service.MemberServiceImpl;
import iducs.java.pim201912014.view.MemberView;
import iducs.java.pim201912014.view.TUIView;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PimController {
    // JCF : Java Collection Framework -
    // 집합 객체를 효과적으로 다루기 위한 자료구조, 알고리즘 등을 포함하는 클래스 라이브러리
    // ArrayList, Stack
    public static Map<String, Member> session = new HashMap<>(); // static : 메모리 상주
    public static TUIView tuiView = new TUIView();
    final String MemberDB = "db201912014.txt"; // 파일명, 디렉터리와 파일명으로 식별 가능함
    Member member = null;

    MemberService<Member> memberService;
    MemberView memberView = null;

    public PimController() {
        memberService = new MemberServiceImpl<>(MemberDB);
        memberView = new MemberView();
    }

    public void dispatch() { // 가져오기 : 메뉴보이기, 선택한 메뉴 처리하기, 결과 반환 반복
        boolean isLogin = false; // 지역변수는 선언된 블록이 종료되면 메모리에서 사라짐
        boolean isRoot = false;
        Scanner sc = new Scanner(System.in); // 키보드 입력을 받아서 분석 반환
        memberService.readFile(); // 처음 실행 시 읽을 파일이 없음
        int menu = 0;
        do {
            Member sessionMember = session.get("member");
            if(sessionMember != null){
                isLogin =true;
                if(sessionMember.getEmail().contains("admin"))
                    isRoot=true;
            }
            else{
                isLogin=false;
                isRoot=false;
            }
            String msg = "";
            tuiView.showMenu(isLogin, isRoot);
            menu = sc.nextInt(); // 숫자 입력 후 엔터키
            switch(menu) {
                case 0: msg = "종료";
                    memberService.saveFile(); // memberdb.txt 에 저장
                    break;
                case 1: msg = "등록";
                    member = new Member();

                    System.out.print("Email : ");
                    member.setEmail(sc.next());

                    System.out.print("Pw : ");
                    member.setPw(sc.next());

                    System.out.print("Name : ");
                    member.setName(sc.next());

                    System.out.print("Phone : ");
                    member.setPhone(sc.next());

                    System.out.print("Address : ");
                    member.setAddress(sc.next());

                    boolean success = memberService.postMember(member);
                    if(success){
                        memberService.saveFile();
                    }
                    memberView.printOne(member);
                    break;
                case 2: msg = "로그인";
                    String emailLogin= new String();
                    String passwdLogin= new String();
                    System.out.println("아이디를 입력하시오 >>");
                    emailLogin=sc.next();
                    System.out.println("암호를 입력하시오 >>");
                    passwdLogin=sc.next();
                    member=(Member)memberService.login(emailLogin,passwdLogin);
                    if(member!=null) {
                        isLogin = true;
                        if(member.getEmail().contains("admin"))
                            isRoot=true;
                        session.put("member",member);
                    }
                    else
                        System.out.println("로그인 정보 확인 바랍니다.");
                    break;
                case 3: msg = "정보조회";
                    if(session.get("member")!=null) {
                        member = memberService.getMember((Member) session.get("member"));
                        memberView.printOne(member);
                    }
                    break;
                case 4: msg = "정보수정";
                    if(session.get("member")!=null) {
                        member = new Member();
                        int num = 0;
                        String msgUpdate = "";
                        member.setId(sessionMember.getId());
                        member.setPw(sessionMember.getPw());
                        member.setName(sessionMember.getName());
                        member.setEmail(sessionMember.getEmail());
                        member.setPhone(sessionMember.getPhone());
                        member.setAddress(sessionMember.getAddress());
                        do {
                            System.out.println("수정 항목 번호를 입력하시오 >>");
                            System.out.println("0. 수정종료 1.이름 2.이메일 3. 연락처 4.주소");
                            num = sc.nextInt();
                            switch (num) {
                                case 0:
                                    msgUpdate = "수정종료";
                                    break;
                                case 1:
                                    System.out.printf("이름을 입력하시오(" + sessionMember.getName() + ") >>");
                                    member.setName(sc.next());
                                    msgUpdate = "이름";
                                    break;
                                case 2:
                                    System.out.printf("이메일을 입력하시오(" + sessionMember.getEmail() + ") >>");
                                    member.setEmail(sc.next());
                                    msgUpdate = "이메일";
                                    break;
                                case 3:
                                    System.out.printf("연락처를 입력하시오(" + sessionMember.getPhone() + ") >>");
                                    member.setPhone(sc.next());
                                    msgUpdate = "연락처";
                                    break;
                                case 4:
                                    System.out.printf("주소를 입력하시오(" + sessionMember.getAddress() + ") >>");
                                    member.setAddress(sc.next());
                                    msgUpdate = "주소";
                                    break;
                                default:
                                    msgUpdate = "입력 코드 확인 :";
                                    break;
                            }
                            System.out.println(msgUpdate + " 메뉴를 선택하셨습니다. ");
                        } while (num != 0);
                        if (memberService.putMember(member)) {
                            memberView.printOne(member);
                            memberService.saveFile();
                        } else
                            System.out.println("수정에 실패하였습니다.");
                    }
                    break;
                case 5: msg = "로그아웃";
                    Member ret = session.get("member");
                    if(ret != null){
                        session.remove("member");
                    }
                    break;
                case 6: msg = "회원탈퇴";
                    if(session.get("member")!=null){
                        if(memberService.deleteMember(member)){
                            session.remove("member");
                            //memberView.printList(memberService.getMemberList());
                            System.out.println("회원탈퇴에 성공하였습니다.");
                            memberService.saveFile();
                        }
                        else{
                            System.out.println("회원탈퇴에 실패하였습니다.");
                        }
                    }
                    break;
                case 7:
                    if(isRoot!=false)
                        memberView.printList(memberService.getMemberList());
                    else
                        System.out.println("관리자가 아닙니다.");
                    msg = "회원목록조회";
                    break;
                case 8 : msg="회원 전화번호 검색";
                    member = new Member();
                    if(isRoot!=false) {
                        System.out.println("전화번호를 입력하시오 >>");
                        member.setPhone(sc.next());
                        memberView.printList(memberService.findMemberByPhone(member));
                    }
                    else
                        System.out.println("관리자가 아닙니다.");
                    break;
                case 9 : msg="회원 이름 내림차순으로 정렬";
                    int sortNum = 0;
                    String sort ="";
                    if(isRoot!=false) {
                        do {
                            System.out.println("0. 종료 1.내림차순 2.오름차순");
                            sortNum = sc.nextInt();
                            switch (sortNum){
                                case 0:
                                    break;
                                case 1:
                                    sort = "desc";
                                    memberView.printList(memberService.sortByName(sort));
                                    break;
                                case 2 :
                                    sort = "asc";
                                    memberView.printList(memberService.sortByName(sort));
                                    break;
                                default:
                                    System.out.println("1 또는 2를 입력해주세요.");
                                    break;
                            }
                        }while (sortNum !=0);
                    }
                    else
                        System.out.println("관리자가 아닙니다.");
                    break;
                case 10 : msg ="범위 지정 회원 목록 출력";
                    int pageNo = 0;
                    int perPage = 0;
                    if(isRoot!=false){
                        System.out.println("페이지를 지정하세요 >>");
                        pageNo=sc.nextInt();
                        System.out.println("범위를 지정하세요 >>");
                        perPage=sc.nextInt();
                        memberView.printList(memberService.paginateByPerPage(pageNo,perPage));
                    }
                    else
                        System.out.println("관리자가 아닙니다.");
                    break;
                default: msg = "입력 코드 확인 :"; break;
            }
            System.out.println(msg + " 메뉴를 선택하셨습니다. ");
        } while(menu != 0);
    }
}
