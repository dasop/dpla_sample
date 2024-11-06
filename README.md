# D.pla API


## 환경 설정

#### 1. Local DB 세팅

1. [postgresql-15.2-2-windows-x64-binaries.zip](https://kms.ktds.co.kr:8090/display/DigicoBusinessFulfilment/Postgres+Local) 받아서 압축 해제

2. `D:\KTDS_DEV\db` 로 복사 및 폴더명 변경

3. `postgresql-15.2-2-windows-x64-binaries ` ->  `postgresql-15.2-2`

4. `D:\KTDS_DEV\db\postgresql-15.2-2\pgsql\bin` 에서 우클릭 PowerShell 실행

   ```powershell
   # Init
   ./initdb.exe -U postgres -A password -W -E utf8 -D D:\KTDS_DEV\db\postgresql-15.2-2\pgsql\data
   
   # 기동
   ./pg_ctl.exe -D D:\KTDS_DEV\db\postgresql-15.2-2\pgsql\data -l D:\KTDS_DEV\db\postgresql-15.2-2\pgsql\log start
   
   # 정지
   ./pg_ctl.exe -D D:\KTDS_DEV\db\postgresql-15.2-2\pgsql\data -l D:\KTDS_DEV\db\postgresql-15.2-2\pgsql\log stop
   
   # psql 실행
   ./psql.exe -U postgres
   ```

5. psql 실행 후 DB 초기 설정

   ```sql
   -- 계정생성 및 권한 부여
   CREATE USER dpla WITH PASSWORD 'dpla';
   
   -- Database 생성
   CREATE DATABASE dpla with owner dpla encoding 'UTF8';
   

#### 2. API 실행

1. `jdk17` 및 ide(STS 4.16 이상) 설치


2.  `settings.xml` 파일 수정 후 Maven Reload

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <settings>
       <servers>
   		<server>
               <id>central</id>
               <username>계정명</username>
               <password>패스워드</password>
           </server>
       </servers>
       <mirrors>
           <mirror>
               <mirrorOf>central</mirrorOf>
               <id>central</id>
               <url>https://nexus.dspace.kt.co.kr/repository/maven-public/</url>
           </mirror>
       </mirrors>
   </settings>
   ```

3. Run/Debug Configuration 

   * STS
     1. Run/Debug Configuration -> Spring Boot App -> New Configuration -> Application 추가
     2. Main Class 입력
     3. 환경 변수 입력
        * 실행 환경별 spring profile 지정 필요
          * `local` : 로컬 환경 + dev DB
          * `dev` :  개발 환경
          * `tb` :  테스트 환경
          * `prod` :  운영 환경

4. DB 기동 후 Run/Debug 진행

5. 정상 구동 확인
   * Swagger : http://localhost:8080/api/swagger-ui/index.html

