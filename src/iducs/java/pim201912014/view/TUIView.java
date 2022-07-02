package iducs.java.pim201912014.view;

public class TUIView { //TUI : Text User Interface, CUI(Character UI)
    public void showMenu(boolean isLogin, boolean isRoot) {
        if(isLogin == false) {
            System.out.print("0. 종료\t");
            System.out.print("1. 등록\t");
            System.out.print("2. 로그인\n");
        } else {
            if(isRoot == false) {
                System.out.print("3. 정보조회\t");
                System.out.print("4. 정보수정\t");
                System.out.print("5. 로그아웃\t");
                System.out.print("6. 회원탈퇴\n");
            } else {
                System.out.print("0. 종료\t");
                System.out.print("3. 정보조회\t");
                System.out.print("4. 정보수정\t");
                System.out.print("5. 로그아웃\t");
                System.out.print("7. 목록조회\t");
                System.out.print("8. 회원 전화번호 검색\t");
                System.out.print("9. 회원 이름 내림차순으로 정렬\t");
                System.out.println("10. 범위 지정 회원 목록");
            }
        }
    }
}
