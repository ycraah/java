package com.ycraah.javaboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {
    System.out.println("==프로그램 시작==");
    Scanner sc = new Scanner(System.in); //스캐너
    int id = 0; //게시물 번호
    int lastId = 0; //최근 게시물 번호
    List<Article> articles = new ArrayList<>(); //게시물 번호, 제목, 내용를 담은 객체 articles

    IntStream.rangeClosed(1, 3).forEach(
        i -> articles.add(new Article("테스트 제목" + i, "테스트 내용" + i, i))); //test용 삭제 예정

    while(true){
      System.out.print("명령) ");
      String cmd = sc.nextLine();

      if(cmd.equalsIgnoreCase("write")){
        System.out.println("게시물 등록");
        System.out.print("제목) ");
        String title = sc.nextLine();
        System.out.print("내용) ");
        String content = sc.nextLine();

        id = articles.size() + 1; //articles의 배열 길이 + 1을 하여 게시물 번호로 지정
        Article article = new Article(title, content, id); // 게시판 정보를 담은 article 형성
        articles.add(article); //articles에 article을 저장
        lastId = id; //최근 게시물 번호 저장

        System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
        System.out.println(article.toString());

      } else if(cmd.equalsIgnoreCase("exit")) {
        System.out.println("프로그램을 종료합니다.");
        break;

      } else if(cmd.equalsIgnoreCase("detail")){
        if(lastId == 0){
          lastId = articles.size() + 1;
        }

        System.out.println("-게시물 상세보기-");
        Article lastArticle = articles.get(lastId-1); //lastId에 의거해서 마지막 게시물 정보 불러오기
        System.out.printf("번호 : %d \n제목 : %s \n내용 : %s\n", lastArticle.id, lastArticle.title, lastArticle.content);
      } else if(cmd.equalsIgnoreCase("list")){
        System.out.println("-게시물 리스트-");
        System.out.println("---------------");
        System.out.println("번호 / 제목");

       /* for(Article article : articles) {//articles에 저장된 내용을 article객체에 저장하여 for문으로 불러내어 출력
          System.out.printf("%d / %s\n", article.id, article.title);
        } 순차 출력*/
        for(int i = articles.size()-1; i >= 0; i--) { //Arraylist는 배열 길이를 size()로 확인!!
          Article article = articles.get(i);
          System.out.printf("%d / %s\n", article.id, article.title); //역순 출력
        }
        System.out.println("---------------");
      }

      else {
        System.out.println("잘못된 명령어 입니다.");
      }
    }

    sc.close(); //스캐너 종료
    System.out.println("==프로그램 종료==");
  }
}

class Article {
  String title;
  String content;
  int id;

  public Article(String title, String content, int id) {
    this.title = title;
    this.content = content;
    this.id = id;
  }

  @Override
  public String toString(){
    return String.format("[번호:%d, 제목:%s, 본문:%s]", this.id, this.title, this.content);
  }
}