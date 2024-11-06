package com.kt.dpla.support.lamp.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kt.dpla.support.lamp.type.SecureEventType;
import com.kt.dpla.support.lamp.type.SecureType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@ToString
@Builder
@JsonInclude(NON_NULL)
public class Security {
	private SecureType type;
	private SecureEventType event;
	private	String target;
	private	String multiTarget;
	private	String multiTargetCount;
	private	String detail;
	private	String reason;
	private	String readOther;
	private	String personalInfoList;
	private	String exported;

	public static Security createLogin() {
		return Security.builder()
				.type(SecureType.ACCESS)
				.event(SecureEventType.LOGIN)
				.build();
	}

	public static Security createLogout() {
		return Security.builder()
				.type(SecureType.ACCESS)
				.event(SecureEventType.LOGOUT)
				.build();
	}

	/**
	 *
	 * @param target
	 * 개인정보처리의 대상 권한부여,변경의 대상 ※ 사용자ID와 동일하거나 다를 수 있음
	 * - CREATE : 대상자 ID
	 * @param personalInfoList
	 * 화면에 노출되는 개인정보 항목 리스트
	 * (다량대상자인 필드만 입력 (값제외))
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * - CREATE : 회원가입 시 작성한 개인정보 내역, 필수암호화 대상인 경우 필드만
	 * @param detail
	 * 개인정보변경 내역이나, 권한변경 내역, 인증수행목적
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 *- CREATE : 회원가입
	 * @return
	 */
	public static Security createCreate(String target, String personalInfoList, String detail) {
		return Security.builder()
				.type(SecureType.UPIP)
				.event(SecureEventType.CREATE)
				.target(target)
				.detail(detail)
				.personalInfoList(personalInfoList)
				.build();
	}

	/**
	 *
	 * @param target
	 * 개인정보처리의 대상 권한부여,변경의 대상 ※ 사용자ID와 동일하거나 다를 수 있음
	 * - READ : 대상자 ID
	 * @param multiTarget
	 * 개인정보 조회시 다량의 개인정보인 경우 사용(조회시 사용된 쿼리 문)
	 * - READ: 요청 body
	 * @param multiTargetCount
	 * 개인정보 조회시 다량의 개인정보인 경우 사용 (조회된 개인정보 수)
	 * - READ: 조회된 개인정보 수
	 * @param reason
	 * 해당 작업을 수행한 이유(마스킹 정보 해제시)
	 * - READ : 마스킹 해제 이유
	 * @param readOther
	 * 타인의 개인정보를 조회할 수 있는 기능이 있는 경우를 구분하기 위한 목적
	 * ※일반적인 경우 타인의 개인정보를 조회하는 것은 침해 사례에 해당하나 시스템에서 기능적으로 타인의 개인정보 조회 기능을 제공하는 경우(사전 동의절차를 거쳐 가족이 대리로 업무 처리 등)이를 구분하여 알람 대상에서 제외하기 위한 목적으로 활용
	 * (ex. 로그상의 user.id 값과 security.target 값이 다르지만 security.readOther 값이 'Y'일 경우 정상적인 상황으로 판단)
	 * - READ : Y/N
	 * @param personalInfoList
	 * 화면에 노출되는 개인정보 항목 리스트
	 * (다량대상자인 필드만 입력 (값제외))
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * - READ : 단일정보는 전부 표시, 다량 정보 조회시 항목만
	 * @return
	 */
	public static Security createRead(String target, String multiTarget, String multiTargetCount, String reason,String readOther, String personalInfoList) {
		return Security.builder()
				.type(SecureType.UPIP)
				.event(SecureEventType.READ)
				.target(target)
				.multiTarget(multiTarget)
				.multiTargetCount(multiTargetCount)
				.reason(reason)
				.readOther(readOther)
				.personalInfoList(personalInfoList)
				.build();
	}

	/**
	 *
	 * @param multiTarget
	 * 개인정보 조회시 다량의 개인정보인 경우 사용(조회시 사용된 쿼리 문)
	 * - READ: 요청 body
	 * @param reason
	 * 해당 작업을 수행한 이유(마스킹 정보 해제시)
	 * - READ : 마스킹 해제 이유
	 * @param readOther
	 * 타인의 개인정보를 조회할 수 있는 기능이 있는 경우를 구분하기 위한 목적
	 * ※일반적인 경우 타인의 개인정보를 조회하는 것은 침해 사례에 해당하나 시스템에서 기능적으로 타인의 개인정보 조회 기능을 제공하는 경우(사전 동의절차를 거쳐 가족이 대리로 업무 처리 등)이를 구분하여 알람 대상에서 제외하기 위한 목적으로 활용
	 * (ex. 로그상의 user.id 값과 security.target 값이 다르지만 security.readOther 값이 'Y'일 경우 정상적인 상황으로 판단)
	 * - READ : Y/N
	 * @return
	 */
	public static Security createInReqRead(String multiTarget, String reason,String readOther) {
		return Security.builder()
				.type(SecureType.UPIP)
				.event(SecureEventType.READ)
				.multiTarget(multiTarget)
				.reason(reason)
				.readOther(readOther)
				.build();
	}

	/**
	 *
	 * @param target
	 * 개인정보처리의 대상 권한부여,변경의 대상 ※ 사용자ID와 동일하거나 다를 수 있음
	 * - READ : 대상자 ID
	 * @param multiTargetCount
	 * 개인정보 조회시 다량의 개인정보인 경우 사용 (조회된 개인정보 수)
	 * - READ: 조회된 개인정보 수
	 * @param personalInfoList
	 * 화면에 노출되는 개인정보 항목 리스트
	 * (다량대상자인 필드만 입력 (값제외))
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * - READ : 단일정보는 전부 표시, 다량 정보 조회시 항목만
	 * @return
	 */
	public static Security createInResRead(String target, String multiTargetCount,String personalInfoList) {
		return Security.builder()
				.type(SecureType.UPIP)
				.event(SecureEventType.READ)
				.target(target)
				.multiTargetCount(multiTargetCount)
				.personalInfoList(personalInfoList)
				.build();
	}

	/**
	 *
	 * @param target
	 * 개인정보처리의 대상 권한부여,변경의 대상 ※ 사용자ID와 동일하거나 다를 수 있음
	 * - UPDATE : 대상자 ID
	 * @param detail
	 * 개인정보변경 내역이나, 권한변경 내역, 인증수행목적
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * UPDATE : 변경항목. 필수 암호화대상인 경우 내용만 ex) CHG_password, 가입승인
	 * @param personalInfoList
	 * 화면에 노출되는 개인정보 항목 리스트
	 * (다량대상자인 필드만 입력 (값제외))
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * - UPDATE : 변경된 항목 포함하여 전체 출력된 정보를 작성. 가입승인인 경우 생략
	 * @return
	 */
	public static Security createUpdate(String target, String detail, String personalInfoList) {
		return Security.builder()
				.type(SecureType.UPIP)
				.event(SecureEventType.UPDATE)
				.target(target)
				.detail(detail)
				.personalInfoList(personalInfoList)
				.build();
	}

	/**
	 *
	 * @param target
	 * 개인정보처리의 대상 권한부여,변경의 대상 ※ 사용자ID와 동일하거나 다를 수 있음
	 * EXPORT : 대상자 ID
	 * @param multiTarget
	 * 개인정보 조회시 다량의 개인정보인 경우 사용(조회시 사용된 쿼리 문)
	 * - READ: 요청 body
	 * @param multiTargetCount
	 * 개인정보 조회시 다량의 개인정보인 경우 사용 (조회된 개인정보 수)
	 * - EXPORT : 조회된 개인정보 수
	 * @param detail
	 * 개인정보변경 내역이나, 권한변경 내역, 인증수행목적
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 *  - EXPORT(개인정보 출력) : 출력 방식 표시(ex. File:Excel, File:Word, Print)
	 * @param reason
	 * 해당 작업을 수행한 이유(마스킹 정보 해제시)
	 * - READ : 마스킹 해제 이유
	 * @param personalInfoList
	 * 화면에 노출되는 개인정보 항목 리스트
	 * (다량대상자인 필드만 입력 (값제외))
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * - EXPORT : 단일정보는 전부 표시, 다량 정보 조회시 항목만
	 * @param exported
	 * 출력한 파일명
	 * - EXPORT : 출력한 파일명
	 * @return
	 */
	public static Security createExport(String target, String multiTarget, String multiTargetCount, String detail, String reason, String personalInfoList, String exported) {
		return Security.builder()
				.type(SecureType.UPIP)
				.event(SecureEventType.EXPORT)
				.target(target)
				.multiTarget(multiTarget)
				.multiTargetCount(multiTargetCount)
				.detail(detail)
				.reason(reason)
				.personalInfoList(personalInfoList)
				.exported(exported)
				.build();
	}
	/**
	 * @param multiTarget
	 * 개인정보 조회시 다량의 개인정보인 경우 사용(조회시 사용된 쿼리 문)
	 * - EXPORT: 요청 body
	 * @param detail
	 * 개인정보변경 내역이나, 권한변경 내역, 인증수행목적
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 *  - EXPORT(개인정보 출력) : 출력 방식 표시(ex. File:Excel, File:Word, Print)
	 * @param reason
	 * 해당 작업을 수행한 이유(마스킹 정보 해제시)
	 * - READ : 마스킹 해제 이유
	 * @param exported
	 * 출력한 파일명
	 * - EXPORT : 출력한 파일명
	 * @return
	 */
	public static Security createInReqExport(String multiTarget, String detail, String reason, String exported) {
		return Security.builder()
				.type(SecureType.UPIP)
				.event(SecureEventType.EXPORT)
				.multiTarget(multiTarget)
				.detail(detail)
				.reason(reason)
				.exported(exported)
				.build();
	}
	/**
	 * @param target
	 * 개인정보처리의 대상 권한부여,변경의 대상 ※ 사용자ID와 동일하거나 다를 수 있음
	 * EXPORT : 대상자 ID
	 * @param multiTargetCount
	 * 개인정보 조회시 다량의 개인정보인 경우 사용 (조회된 개인정보 수)
	 * - EXPORT : 조회된 개인정보 수
	 * @param personalInfoList
	 * 화면에 노출되는 개인정보 항목 리스트
	 * (다량대상자인 필드만 입력 (값제외))
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * - EXPORT : 단일정보는 전부 표시, 다량 정보 조회시 항목만
	 * @return
	 */
	public static Security createInResExport(String target, String multiTargetCount, String personalInfoList) {
		return Security.builder()
				.type(SecureType.UPIP)
				.event(SecureEventType.EXPORT)
				.target(target)
				.multiTargetCount(multiTargetCount)
				.personalInfoList(personalInfoList)
				.build();
	}


	/**
	 *
	 * @param target
	 * 개인정보처리의 대상 권한부여,변경의 대상 ※ 사용자ID와 동일하거나 다를 수 있음
	 * - DELETE : 대상자 ID
	 * @param detail
	 * 개인정보변경 내역이나, 권한변경 내역, 인증수행목적
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 *  - DELETE : 회원탈퇴, 계정삭제 등
	 * @param personalInfoList
	 * 화면에 노출되는 개인정보 항목 리스트
	 * @return
	 */
	public static Security createDelete(String target, String detail, String personalInfoList) {
		return Security.builder()
				.type(SecureType.UPIP)
				.event(SecureEventType.DELETE)
				.personalInfoList(personalInfoList)
				.target(target)
				.detail(detail)
				.build();
	}

	/**
	 *
	 * @param target
	 * 개인정보처리의 대상 권한부여,변경의 대상 ※ 사용자ID와 동일하거나 다를 수 있음
	 * - PASS : pass 인증키
	 * @param detail
	 * 개인정보변경 내역이나, 권한변경 내역, 인증수행목적
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * @param personalInfoList
	 * 화면에 노출되는 개인정보 항목 리스트
	 * (다량대상자인 필드만 입력 (값제외))
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * - PASS : FIND_ID => ID를 찾기 위해 입력한 값 등
	 * @return
	 */
	public static Security createPass(String target, String detail, String personalInfoList) {
		return Security.builder()
				.type(SecureType.AUTH)
				.event(SecureEventType.PASS)
				.target(target)
				.detail(detail)
				.personalInfoList(personalInfoList)
				.build();
	}

	/**
	 *
	 * @param target
	 * 개인정보처리의 대상 권한부여,변경의 대상 ※ 사용자ID와 동일하거나 다를 수 있음
	 * - IPIN : 아이핀 ID
	 * @param detail
	 * 개인정보변경 내역이나, 권한변경 내역, 인증수행목적
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * @param personalInfoList
	 * 화면에 노출되는 개인정보 항목 리스트
	 * (다량대상자인 필드만 입력 (값제외))
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * - IPIN : FIND_ID => ID를 찾기 위해 입력한 값 등
	 * @return
	 */
	public static Security createIpin(String target, String detail, String personalInfoList) {
		return  Security.builder()
				.type(SecureType.AUTH)
				.event(SecureEventType.IPIN)
				.target(target)
				.detail(detail)
				.personalInfoList(personalInfoList)
				.build();
	}

	/**
	 *
	 * @param target
	 * 개인정보처리의 대상 권한부여,변경의 대상 ※ 사용자ID와 동일하거나 다를 수 있음
	 * - EMAIL : 이메일 주소
	 * @param detail
	 * 개인정보변경 내역이나, 권한변경 내역, 인증수행목적
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * @param personalInfoList
	 * 화면에 노출되는 개인정보 항목 리스트
	 * (다량대상자인 필드만 입력 (값제외))
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * - EMAIL : FIND_ID => ID를 찾기 위해 입력한 값 등
	 * @return
	 */
	public static Security createEmail(String target, String detail, String personalInfoList) {
		return  Security.builder()
				.type(SecureType.AUTH)
				.event(SecureEventType.EMAIL)
				.target(target)
				.detail(detail)
				.personalInfoList(personalInfoList)
				.build();
	}

	/**
	 *
	 * @param target
	 * 개인정보처리의 대상 권한부여,변경의 대상 ※ 사용자ID와 동일하거나 다를 수 있음
	 * - PHONE : 핸드폰 번호
	 * @param detail
	 * 개인정보변경 내역이나, 권한변경 내역, 인증수행목적
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * @param personalInfoList
	 * 화면에 노출되는 개인정보 항목 리스트
	 * (다량대상자인 필드만 입력 (값제외))
	 * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
	 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
	 * - PHONE : FIND_ID => ID를 찾기 위해 입력한 값 등
	 * @return
	 */
	public static Security createPhone(String target, String detail, String personalInfoList) {
		return  Security.builder()
				.type(SecureType.AUTH)
				.event(SecureEventType.PHONE)
				.target(target)
				.detail(detail)
				.personalInfoList(personalInfoList)
				.build();
	}
}
