package com.ycraah.javaboard;

import java.util.*; //최상단에 없으면 오류 발생
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    System.out.println("==프로그램 시작==");
    Scanner sc = new Scanner(System.in);
    List<Article> articles = new ArrayList<>(); //게시물 번호, 제목, 내용를 담은 객체 articles

    IntStream.rangeClosed(1, 100).forEach(
        i -> articles.add(new Article("테스트 제목" + i, "테스트 내용" + i, i))); //test용 데이터

    while(true){ //명령어 무한 입력을 위한 반복문
      System.out.print("명령) ");
      String cmd = sc.nextLine();
      Rq rq = new Rq(cmd); //Url 파싱을 위한 Rq 객체 형성

      if(rq.urlPath.equalsIgnoreCase("write")){
        System.out.println("게시물 등록");
        System.out.print("제목) ");
        String title = sc.nextLine();

        if(title.trim().isEmpty()){
          System.out.println("제목을 입력해주세요.");
          continue;
        }

        System.out.print("내용) ");
        String content = sc.nextLine();

        if(content.trim().isEmpty()){
          System.out.println("내용을 입력해주세요.");
          continue;
        }

        int id = articles.size() + 1; //articles의 배열 길이 + 1을 하여 게시물 번호로 지정
        Article article = new Article(title, content, id); // 게시판 정보를 담은 article 형성
        articles.add(article); //articles에 article을 저장

        System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
        System.out.println(article.toString());

      } else if(rq.urlPath.equalsIgnoreCase("detail")){
        if(articles.isEmpty()){
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        if(!rq.params.containsKey("id")){
          System.out.println("id를 입력해주세요.");
          continue;
        }

        int id = 0;

          try{ //유효성 검사
            id = Integer.parseInt(rq.params.get("id"));
          } catch(NumberFormatException e){
            System.out.println("id를 정수 형태로 입력해주세요");
            continue;
        }

        System.out.println("-게시물 상세보기-");
        Article detailArticle = articles.get(id - 1);
        System.out.printf("번호 : %d \n제목 : %s \n내용 : %s\n", detailArticle.id, detailArticle.title, detailArticle.content);

      } else if(rq.urlPath.equalsIgnoreCase("list")){
        if(articles.isEmpty()){
          System.out.println("게시물이 없습니다.");
          continue;
        }

        boolean orderByIdDesc = true;

        if(rq.params.containsKey("orderBy")){ //orderBy는 정렬
          if(rq.params.get("orderBy").equals("idAsc"))
            orderByIdDesc = true;
          else if(rq.params.get("orderBy").equals("Desc"))
            orderByIdDesc = false;
          else{
            System.out.println("올바르지 않은 정렬 방식입니다.");
            continue;
          }
        } else if(rq.params.containsKey("searchKeyword")) { //searchKeyword 입력 시 검색
          String searchKeyword = rq.params.get("searchKeyword");
          if(searchKeyword != ""){
            System.out.println("번호 / 제목");
            for(Article article : articles){
              if(article.title.contains(searchKeyword) || article.content.contains(searchKeyword)){
                System.out.printf("%d / %s\n", article.id, article.title);}
              }
              continue;
            } else {
            System.out.println("검색할 키워드를 입력하세요.");
            continue;
          }
        } else { //orderBy가 없을 시
          System.out.println("정렬 방식을 입력해주세요");
          continue;
        }

        System.out.println("-게시물 리스트-");
        System.out.println("---------------");
        System.out.println("번호 / 제목");

        if(orderByIdDesc){
          for(Article article : articles) {
            System.out.printf("%d / %s\n", article.id, article.title);} //정순순회
        } else {
          for(int i = articles.size()-1; i >= 0; i--) { //Arraylist는 배열 길이를 size()로 확인!!
            Article article = articles.get(i);
            System.out.printf("%d / %s\n", article.id, article.title);} //역순순회
        }
        System.out.println("---------------");

      } else if(rq.urlPath.equalsIgnoreCase("exit")) {
        System.out.println("프로그램을 종료합니다.");
        break;

      } else {
        System.out.println("잘못된 명령어 입니다.");
      }
    }//end of while

    sc.close(); //스캐너 종료
    System.out.println("==프로그램 종료==");
  } //end of main
} //end of Main

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

class Rq {
  String url;   ///URL의 구성 : protocol://Domain/Path?Parameter(or QueryString)#Fragment
  Map<String, String> params;
  String urlPath;

  Rq(String url) {
    this.url = url; // ex) detail?page=2&searchKeyword=안녕? 나는
    params = Util.getParamsFromUrl(url); //[0] (page, 2) [1] (searchKeyword, 안녕? 나는)
    urlPath = Util.getUrlPathFromUrl(url); //detail
  }

  public String getUrlPath(){ //detail
    return url;
  }

  public Map<String, String> getParams(){ //[0] (page, 2) [1] (searchKeyword, 안녕? 나는)
    return params;
  }
}

class Util{
  static String getUrlPathFromUrl(String url){ // ex) datail?page=2&searchKeyword=안녕? 나는
    return url.split("\\?", 2)[0]; //detail
  }

  static Map<String, String> getParamsFromUrl(String url){
    Map<String, String> params = new HashMap<>();
    String[] urlBits = url.split("\\?", 2); //[0]detail [1]page=2&searchKeyword=안녕? 나는

    if(urlBits.length == 1){
      return params;
    }

    String queryStr = urlBits[1]; //page=2&searchKeyword=안녕? 나는
    for(String bit : queryStr.split("&")){ //[0]page=2 [1]searchKeyword=안녕? 나는
      String[] bits = bit.split("=", 2); //[0]page [1]2 //[0]searchKeyword [1]안녕? 나는

      if(bits.length == 1){
        continue;
      }
      params.put(bits[0], bits[1]); //(page, 2)를 세트로 묶어서 params에 저장
    }
    return params; //[0] (page, 2) [1] (searchKeyword, 안녕? 나는)
  }
}


