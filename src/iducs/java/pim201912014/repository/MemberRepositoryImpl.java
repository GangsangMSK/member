package iducs.java.pim201912014.repository;

import iducs.java.pim201912014.domain.Member;

import java.time.Period;
import java.util.*;

public class MemberRepositoryImpl<T> implements MemberRepository<T> {
    // <> : Generic (제너릭) 1. 컴파일 시점에 유형을 확인 2. 사용시 형변환을 줄여줌
    // 파일 또는 데이터베이스를 접근하여 데이터를 처리함(Data Access : create, read, update, delete ...)
    public static long memberId = 1;
    Member memberDTO = null;
    public List<T> memberList = null;
    public MemberRepositoryImpl() {
        // Array 배열 : (정적인 크기를 가진) 동일한 자료형을 인덱스를 활용하여 접근하는 객체
        memberList = new ArrayList<T>(); // Array + List : (동적 - 늘어남) 배열과 리스트 장점
    }
    @Override
    public boolean create(T member) {
        int count=0;
        try {
            if ((((Member) member).getEmail()).contains("@")) {
                for (T t : memberList) {
                    if ((((Member) member).getEmail()).equals(((Member) t).getEmail())) {
                        System.out.println("중복된 이메일입니다.");
                        return false;
                    }
                    count++;
                }
                ((Member) member).setId(count);
                memberList.add((T) member); // 형변환
                return true; // 성공
            }
            else{
                System.out.println("이메일 형식을 확인하세요.");
                return false;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public T readById(T member) {
        return null;
    }

    @Override
    public T readByEmail(T member) {
        for (T m :
                memberList) {
            if (((Member)m).getEmail().equals(((Member)member).getEmail()) && ((Member)m).getPw().equals(((Member)member).getPw()))
                return m;
        }
        return null;
    }

    @Override
    public List<T> readList() {
        return memberList;
    }

    @Override
    public boolean update(T member) {
        long mId=0;
        Long tId=0L;
        int tIdInt=0;
        for (T t : memberList) {
            mId= ((Member)member).getId();
            tId=((Member)member).getId();
            if (mId == tId){
                tIdInt=tId.intValue();
                memberList.set(tIdInt,member);
            }
        }
        return true;
    }

    @Override
    public boolean delete(T member) {
        int count = 0;
        Long tId=((Member)member).getId();
        int tIdInt=tId.intValue();
        memberList.remove(tIdInt);
        for (T t : memberList) {
            if(tId < ((Member) t).getId()){
                ((Member) t).setId(count);
            }
            count++;
        }
        return true;
    }

    @Override
    public List<T> getMemberList() {
        return this.memberList;
    }

    @Override
    public void setMemberList(List<T> memberList) {
        this.memberList = memberList;
    }

    @Override
    public List<T> readListByPhone(T member) {
        List<T> tmpMemberList = new ArrayList<>();
        for (T t : memberList) {
            String memberPhone = ((Member) member).getPhone();
            String tPhone = ((Member) t).getPhone();
            if (memberPhone.equals(tPhone) || (memberPhone.contains(tPhone.substring(tPhone.length() - 4)))) {
                 tmpMemberList.add(t);
            }
        }
        return tmpMemberList;
    }

    @Override
    public List<T> readListByName(String order) {
        List<T> tmpMemberList = new ArrayList<>();
        tmpMemberList.addAll(memberList);
        if(order.equals("asc")){
            tmpMemberList.sort((o1, o2) -> (((Member)o1).getName().compareTo(((Member)o2).getName())));
        }
        else if(order.equals("desc")) {
            tmpMemberList.sort((o1, o2) -> (((Member)o2).getName().compareTo(((Member)o1).getName())));
        }
        return tmpMemberList;
    }

    @Override
    public List<T> readListByPerPage(int page, int perPage) {
        List<T> tmpMemberList = new ArrayList<>();
        long mId=0;
        for (T t: memberList) {
            mId=((Member) t).getId();
            if(mId > page * perPage -perPage && mId <= page * perPage) {
                tmpMemberList.add(t);
            }
        }
        return tmpMemberList;
    }
}