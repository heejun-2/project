package project_2.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javaexp2.a01_database.DB;
import project_2.P01_Part1;
import project_2.vo.PJAnnual;
import project_2.vo.PJApproval;
import project_2.vo.PJAttendance;
import project_2.vo.PJCertificate;
import project_2.vo.PJChange;
import project_2.vo.PJContract;
import project_2.vo.PJDept;
import project_2.vo.PJDraft;
import project_2.vo.PJEmp;
import project_2.vo.PJMgr;
import project_2.vo.PJSalary;
import project_2.vo.PJSalary02;
import project_2.vo.PJWorkSystem;
import project_2.vo.PJ_ApplyAtt;
import project_2.vo.PJ_showAllTime;
import project_2.vo.PJ_showAnnual;

public class project_main {
	Scanner sc = new Scanner(System.in);
	
	private Connection con;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	/*
	  # 해야할 일
	  * 사원 기초 정보(이름, 성별, 이메일주소, 연락처, 주소, 부서)를 입력한 이후 *
	  1. 계정 생성[완료]
	  	- 초기 id, pass를 정해진 규칙에 맞게 수정 메서드를 통해 입력한다.
	 * 	-> 기초정보, 계정 생성 완료시 입력한 내용 출력.
	  2. id&pass 입력시 로그인
	  3. 비밀번호 설정. if문안에 수정 메서드를 통해 변경.
	  4. 비밀번호 재설정. -> 관리자에게 수정요청 -> 비밀번호초기화 메서드를 통해 변경.
	  
	  PJEmp
	  - 사원기초정보입력
	  - 계정생성
	  - 사원로그인
	  
	  PJMgr
	  - 관리자로그인
	  
	  PJChange
	  - 비밀번호 변경
	  - 비밀번호 초기화
	  - 개인정보 변경
	  
	  메서드 자체
	  - 퇴사사 데이터 삭제
	*/
	
	/*
	  ### 로그인&회원가입 로직
	*/
	// 부서정보 입력
	public void insertDeptInfo(PJDept ins) {
		String sql = "INSERT INTO PJDEPT VALUES(?, ?)";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ins.getDeptno());
			pstmt.setString(2, ins.getDname());
			int cnt = pstmt.executeUpdate();
			System.out.println("등록건수:"+cnt);
			con.commit();
			System.out.println("등록성공!!");
			
		} catch (SQLException e) {
	         System.out.println("DB관련 예외:" + e.getMessage());
	         try {
	            con.rollback();
	         } catch (SQLException e1) {
	            System.out.println("rollback에러:" + e1.getMessage());
	         }
	      } catch (Exception e) {
	         System.out.println("기타 에러:" + e.getMessage());
	      } finally {
	         DB.close(pstmt, con);
	      }
	}
	
	// 사원기초정보 입력
	public void insertEmpInfo(PJEmp ins) {
		String sql = "INSERT INTO PJEMP(empno, ename, registnumber, gender, job, indate, email, phone, address, annual, deptno) values('E'||emp_seq.nextval, ?, ?, ?, \r\n"
				+ "?, to_date(?, 'YYYY/MM/DD'), ?, ?, ?, 15, ?)";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ins.getEname());
			pstmt.setString(2, ins.getRegistnumber());
			pstmt.setString(3, ins.getGender());
			pstmt.setString(4, ins.getJob());
			pstmt.setString(5, ins.getIndate());
			pstmt.setString(6, ins.getEmail());
			pstmt.setString(7, ins.getPhone());
			pstmt.setString(8, ins.getAddress());
			pstmt.setString(9, ins.getDeptno());
			pstmt.executeUpdate();
			con.commit();
			System.out.println("기초정보입력 완료!");
			
		} catch (SQLException e) {
	         System.out.println("DB관련 예외:" + e.getMessage());
	         try {
	            con.rollback();
	         } catch (SQLException e1) {
	            System.out.println("rollback에러:" + e1.getMessage());
	         }
	      } catch (Exception e) {
	         System.out.println("기타 에러:" + e.getMessage());
	      } finally {
	         DB.close(pstmt, con);
	      }
	}
	// 기초정보입력 스캐너 메서드
	public void insertEmpInfo() {
		project_main dao = new project_main();
		System.out.println("[사원기초정보 입력]");
		System.out.println("이름을 입력하세요.");
		String ename = sc.nextLine();
		System.out.println("주민등록번호를 입력하세요. ex)@@@@@@-@@@@@@@");
		String registnumber = sc.nextLine();
		System.out.println("성별을 입력하세요.");
		String gender = sc.nextLine();
		System.out.println("직책을 입력하세요.");
		String job = sc.nextLine();
		System.out.println("입사일자를 입력하세요.");
		String indate = sc.nextLine();
		System.out.println("이메일주소를 입력하세요.");
		String email = sc.nextLine();
		System.out.println("연락처를 입력하세요.");
		String phone = sc.nextLine();
		System.out.println("주소를 입력하세요.");
		String address = sc.nextLine();
		System.out.println("부서를 입력하세요.");
		String deptno = sc.nextLine();
		// 사원기초정보 입력(사원번호, 이름, 성별, 직책, 입사일자, 이메일주소, 연락처, 주소, 부서)
		dao.insertEmpInfo(new PJEmp(ename, registnumber, gender, job, indate, email, phone, address, deptno));
		
	}
		
		
	
	// 계정생성메서드 [초기 id, pass설정] -> 수정메서드 사용해서
	public void makeAccount(PJEmp make) {
		String sql = "UPDATE PJEMP\r\n"
				+ "SET id = ?,\r\n"
				+ "	pass = ?\r\n"
				+ "WHERE empno = ?";
		try {
			con = DB.con();
			
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, make.getId());
			pstmt.setString(2, make.getPass());
			pstmt.setString(3, make.getEmpno());
			
			System.out.println("수정건수:"+pstmt.executeUpdate());
			
			con.commit();
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback에러:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("기타 에러:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 계정생성 스캐너 메서드
	public void makeAccount() {
		project_main dao = new project_main();
		System.out.println("[계정 생성] - 관리자");
		System.out.println("설정할 초기 id를 입력하세요. [영어이니셜+입사년도+사원번호]");
		String id = sc.nextLine();
		System.out.println("설정할 초기 비밀번호를 입력하세요. [생년월일 6자리]");
		String pass = sc.nextLine();
		System.out.println("계정생성할 사원의 사원번호를 입력하세요.");
		// empno를 전역변수로?
		String makeEmpno = sc.nextLine();
		System.out.println("계 정 생 성 중 . . ");
		// 사원 계정 생성
		dao.makeAccount(new PJEmp(id, pass, makeEmpno));
		System.out.println("계 정 생 성 완 료 !");
	}
	
	
	
	// 퇴사 예정자 데이터 삭제 및 계정 삭제
	public void deleteAccount(String empno) {
		String sql = "DELETE PJEMP\r\n"
				+ "WHERE empno = ?";
		try {
			con = DB.con();
			
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, empno);
			
			System.out.println("삭제건수:"+pstmt.executeUpdate());
			
			con.commit();
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback에러:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("기타 에러:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 퇴사자 데이터 삭제 스캐너 메서드
	public void deleteAccount() {
		project_main dao = new project_main();
		System.out.println("[퇴사자 데이터 삭제] - 관리자");
		System.out.println("퇴사할 사원의 사원번호를 입력하세요.");
		String delEmpno = sc.nextLine();
		dao.deleteAccount(delEmpno);
		System.out.println("삭 제 완 료 ! !");
	}
	
	
	//관리자 로그인
	public boolean mgrlogin(PJMgr sch) {
		boolean isLogin = false;
		String sql = "SELECT *\r\n"
				+ "FROM PJMGR\r\n"
				+ "WHERE id = ? AND pass = ?";
		try {
	         con = DB.con();
	         pstmt = con.prepareStatement(sql);
	         pstmt.setString(1, sch.getId());
	         pstmt.setString(2, sch.getPass());
	         
	         rs = pstmt.executeQuery();
	         isLogin = rs.next();
	         
	      } catch (SQLException e) {
	         System.out.println("DB관련 에러 발생:" + e.getMessage());
	      } catch (Exception e) {
	         System.out.println("기타 예외 발생:" + e.getMessage());
	      } finally {
	         DB.close(rs, pstmt, con);
	      }
		return isLogin;
	}
	// 관리자로그인 스캐너 메서드
	public void mgrlogin() {
		P01_Part1 dao = new P01_Part1();
		
		boolean isFirst = true;
		PJMgr mLogin = new PJMgr();
		System.out.println("[로그인]");
		do {
			if(!isFirst) {
				System.out.println("로그인 실패! 다시입력해주세요.");
			}
			System.out.println("#로그인");
			System.out.println("id를 입력하세요.");
			String checkId = sc.nextLine();
			System.out.println("비밀번호를 입력하세요");
			String checkPass = sc.nextLine();
			mLogin.setId(checkId);
			mLogin.setPass(checkPass);
			
			isFirst = false;
			
		}while(!dao.mgrlogin(mLogin));
		System.out.println("로그인 성공!!");
	}
	
	
	
	// 사원로그인
	public boolean emplogin(PJEmp sch) {
		boolean isLogin = false;
		String sql = "SELECT *\r\n"
				+ "FROM PJEMP\r\n"
				+ "WHERE id = ? AND pass = ?";
		
		try {
	         con = DB.con();
	         pstmt = con.prepareStatement(sql);
	         pstmt.setString(1, sch.getId());
	         pstmt.setString(2, sch.getPass());
	         
	         rs = pstmt.executeQuery();
	         isLogin = rs.next();
	         
	      } catch (SQLException e) {
	         System.out.println("DB관련 에러 발생:" + e.getMessage());
	      } catch (Exception e) {
	         System.out.println("기타 예외 발생:" + e.getMessage());
	      } finally {
	         DB.close(rs, pstmt, con);
	      }
		return isLogin;
	}
	// 사원로그인 스캐너 메서드
	public void emplogin() {
		P01_Part1 dao = new P01_Part1();
		boolean isFirst = true;
		PJEmp mLogin = new PJEmp();
		System.out.println("[로그인]");
		do {
			if(!isFirst) {
				System.out.println("로그인 실패! 다시입력해주세요.");
			}
			System.out.println("#로그인");
			System.out.println("id를 입력하세요.");
			String checkId = sc.nextLine();
			System.out.println("비밀번호를 입력하세요");
			String checkPass = sc.nextLine();
			mLogin.setId(checkId);
			mLogin.setPass(checkPass);
			
			isFirst = false;
			
		}while(!dao.emplogin(mLogin));
		System.out.println("로그인 성공!!");
	}

	
	
	// 부서별 사원 목록 열람
	public List<PJEmp> showList(PJEmp show){
		List<PJEmp> elist = new ArrayList<PJEmp>();
		String sql = "SELECT ename, job, email, phone\r\n"
				+ "FROM PJEMP\r\n"
				+ "WHERE deptno = ?";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, show.getDeptno());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				elist.add(new PJEmp(
							rs.getString("ename"),
							rs.getString("job"),
							rs.getString("email"),
							rs.getString("phone")
						));
			}
		} catch (SQLException e) {
			System.out.println("DB관련 에러 발생:"+e.getMessage());
		} catch(Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return elist;
	}
	// 부서별 사원목록 열람 스캐너 메서드
	public void showList() {
		project_main dao = new project_main();
		System.out.println("열람할 부서번호를 입력하세요.");
		String showdept = sc.nextLine();
		System.out.println("이름\t직책\t이메일주소\t전화번호");
		for(PJEmp e : dao.showList(new PJEmp(showdept))) {
			System.out.print(e.getEname()+"\t");
			System.out.print(e.getJob()+"\t");
			System.out.print(e.getEmail()+"\t");
			System.out.print(e.getPhone()+"\n");
		}
	}
	
	
	// 비밀번호 변경
	public void changePass(PJChange change) {
		String sql = "UPDATE PJEMP\r\n"
				+ "SET pass = '"+change.getPass()+"'\r\n"
				+ "WHERE empno = '"+change.getEmpno()+"'";
		try {
			con = DB.con();
			
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, change.getPass());
			pstmt.setString(2, change.getEmpno());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("비밀번호 변경 완료!");
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback에러:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("기타 에러:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 비밀번호변경 스캐너 메서드
	public void changePass() {
		project_main dao = new project_main();
		System.out.println("[비밀번호 변경]");
		System.out.println("변경할 비밀번호 입력");
		String changePass = sc.nextLine();
		System.out.println("사원번호를 입력하세요");
		String cempno = sc.nextLine();
		dao.changePass(new PJChange(changePass, cempno));
		
	}
	// 비밀번호초기화 스캐너 메서드
	public void resetPass() {
		project_main dao = new project_main();
		System.out.println("[비밀번호 초기화]");
		System.out.println("초기화할 비밀번호 입력");
		String resetPass = sc.nextLine();
		System.out.println("비밀번호 초기화할 사원번호를 입력하세요.");
		String resetempno = sc.nextLine();
		dao.changePass(new PJChange(resetPass, resetempno));
		System.out.println("비밀번호 초기화 완료!");
	}
	
	
	
	// 개인정보 변경
	public void changeEmpInfo(PJChange change) {
		String sql = "UPDATE PJEMP\r\n"
				+ "SET ename = '"+change.getEname()+"',\r\n"
				+ "	gender = '"+change.getGender()+"',\r\n"
				+ "	email = '"+change.getEmail()+"',\r\n"
				+ "	phone = '"+change.getPhone()+"',\r\n"
				+ "	address = '"+change.getAddress()+"'\r\n"
				+ "WHERE empno = '"+change.getEmpno()+"'";
		try {
			con = DB.con();
			
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, change.getEname());
			pstmt.setString(2, change.getGender());
			pstmt.setString(3, change.getEmail());
			pstmt.setString(4, change.getPhone());
			pstmt.setString(5, change.getAddress());
			pstmt.setString(6, change.getEmpno());
			pstmt.executeUpdate();
			System.out.println("수정건수:"+pstmt.executeUpdate());
			
			con.commit();
			System.out.println("개인정보 변경완료!!");
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback에러:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("기타 에러:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 개인정보변경 스캐너 메서드
	public void changeEmpInfo() {
		project_main dao = new project_main();
		System.out.println("[개인정보변경]");
		System.out.println("변경할 이름 입력");
		String changeEname = sc.nextLine();
		System.out.println("변경할 성별 입력");
		String changeGender = sc.nextLine();
		System.out.println("변경할 이메일 입력");
		String changeEmail = sc.nextLine();
		System.out.println("변경할 전화번호 입력");
		String changePhone = sc.nextLine();
		System.out.println("변경할 주소 입력");
		String changeAddress = sc.nextLine();
		
		System.out.println("위 내용으로 변경할 사원의 사원번호를 입력하세요.");
		String changeEmpno = sc.nextLine();
		
		dao.changeEmpInfo(new PJChange(changeEname, changeGender, changeEmail, changePhone, changeAddress, changeEmpno));
		
	}
	
	/*
	 * 
	 * 
	 * 
	  ### 근태관리 로직
	  
	  
	  
	*/
	
	// 근무제도 신청 - 사원
	public void applySystem(PJ_ApplyAtt apply) {
		String sql ="INSERT INTO ATTENDANCE(attno, attdate, wsno, empno) "
				+ "VALUES('ATT'||attno_seq.nextval, to_char(to_date(?, 'YYYY/MM/DD'), 'YYYY/MM/DD'), ?, ?)";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, apply.getAttdate());
			pstmt.setInt(2, apply.getWsno());
			pstmt.setString(3, apply.getEmpno());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("신청이 완료되었습니다.");
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 근무제도 신청 Scanner 메서드
	public void applySystem() {
		project_main dao = new project_main();
		System.out.println("[근무제도 신청]");
		System.out.println("근무할 날짜를 입력하세요. ex.2022-09-09");
		String date = sc.nextLine();
		dao.showWorkSystem();
		System.out.println("신청할 근무제도 번호를 입력하세요.");
		int num = Integer.parseInt(sc.nextLine());
		System.out.println("사원번호를 입력하세요.");
		String emp = sc.nextLine();
		dao.applySystem(new PJ_ApplyAtt(date, num, emp));
	}
	
	
	// 근무제도 설정 - 관리자
	public void setSystem(PJWorkSystem set) {
		String sql = "INSERT INTO worksystem "
				+ "VALUES(ws_seq.nextval, ?, to_char(to_date(?, 'HH24:MI'), 'HH24:MI'), to_char(to_date(?, 'HH24:MI'), 'HH24:MI'))";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, set.getWskind());
			pstmt.setString(2, set.getWorkstart());
			pstmt.setString(3, set.getWorkend());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("근무제도 설정이 완료되었습니다.");
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 근무제도 설정 Scanner 메서드
	public void setSystem() {
		project_main dao = new project_main();
		System.out.println("[근무제도 설정]");
		System.out.println("근무제도 종류를 입력하세요. ex)주35시간");
		String kind = sc.nextLine();
		System.out.println("근무 시작시간을 입력하세요.");
		String start = sc.nextLine();
		System.out.println("근무 종료시간을 입력하세요.");
		String end = sc.nextLine();
		dao.setSystem(new PJWorkSystem(kind, start, end));
		
	}
	
	// 근무제도 현황 열람
	public List<PJWorkSystem> showWorkSystem(PJWorkSystem show){
		List<PJWorkSystem> list = new ArrayList<PJWorkSystem>();
		String sql = "SELECT * \r\n"
				+ "FROM worksystem";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new PJWorkSystem(
							rs.getInt("wsno"),
							rs.getString("wskind"),
							rs.getString("workstart"),
							rs.getString("workend")
						));
			}
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return list;
	}
	// 근무제도 현황 Scanner 메서드
	public void showWorkSystem() {
		project_main dao = new project_main();
		System.out.println("[근무제도 현황]");
		System.out.println("제도번호\t종류\t근무시작시간\t근무종료시간");
		for(PJWorkSystem ws : dao.showWorkSystem(new PJWorkSystem())) {
			System.out.print(ws.getWsno()+"\t");
			System.out.print(ws.getWskind()+"\t");
			System.out.print(ws.getWorkstart()+"\t\t");
			System.out.print(ws.getWorkend()+"\n");
		}
	}
	
	/*
	 
	  # 출/퇴근 현황 Part
	  
	*/
	// 출근시간 저장
	public void saveStartTime(PJAttendance save) {
		String sql = "UPDATE ATTENDANCE\r\n"
				+ "SET startwork = to_char(sysdate,'HH24:MI')\r\n"
				+ "WHERE attdate = to_char(to_date(?, 'YYYY/MM/DD'), 'YYYY/MM/DD')\r\n"
				+ "AND empno = ?";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, save.getAttdate());
			pstmt.setString(2, save.getEmpno());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("출근시간이 저장되었습니다.");
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 지각체크를 위한 출근시간 가져오기
	public String lateCheck(String today, String empno) {
		String late = "";
		String sql = "SELECT startwork\r\n"
				+ "FROM ATTENDANCE\r\n"
				+ "WHERE attdate = ?\r\n"
				+ "AND empno = ?";
		try {
			con = DB.con();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, today);
			pstmt.setString(2, empno);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				late = rs.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return late;
	}
	// 지각유뮤 저장
	public void saveLate(String contents, String today, String empno) {
		String sql = "UPDATE ATTENDANCE\r\n"
				+ "SET contents = ?\r\n"
				+ "WHERE attdate = ?\r\n"
				+ "AND empno = ?";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, contents);
			pstmt.setString(2, today);
			pstmt.setString(3, empno);
			pstmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	//출근시간 저장 스캐너 메서드
	public void saveStartTime() {
		project_main dao = new project_main();
		System.out.println("[출근시간 저장]");
		System.out.println("오늘날짜를 입력하세요. ex)2022/10/10");
		String today = sc.nextLine();
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		dao.saveStartTime(new PJAttendance(today, empno));
		String start = dao.lateCheck(today, empno);
		String contents = "";
		if((Integer.parseInt(start.substring(0,2)) >= 9) && (Integer.parseInt(start.substring(3)) > 10)) {
			contents = "지각";
		}else {
			contents = "출근완료";
		}
		dao.saveLate(contents, today, empno);
	}
	
	
	// 퇴근시간 저장
	public void saveEndTime(PJAttendance save) {
		String sql = "UPDATE ATTENDANCE\r\n"
				+ "SET endwork = to_char(sysdate,'HH24:MI')\r\n"
				+ "WHERE attdate = to_char(to_date(?, 'YYYY/MM/DD'), 'YYYY/MM/DD')\r\n"
				+ "AND empno = ?";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, save.getAttdate());
			pstmt.setString(2, save.getEmpno());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("퇴근시간이 저장되었습니다.");
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 퇴근시간 저장 스캐너 메서드
	public void saveEndTime() {
		project_main dao = new project_main();
		System.out.println("[퇴근시간 저장]");
		System.out.println("오늘날짜를 입력하세요. ex)2022/10/10");
		String today = sc.nextLine();
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		dao.saveEndTime(new PJAttendance(today, empno));
		
	}
	
	// 날짜별 출/퇴근시간 현황열람
	public List<PJ_showAllTime> showAllTime(PJ_showAllTime show) {
		List<PJ_showAllTime> list = new ArrayList<PJ_showAllTime>();
		String sql = "SELECT ename, startwork, endwork\r\n"
				+ "FROM PJEMP e , ATTENDANCE a\r\n"
				+ "WHERE e.empno = a.empno\r\n"
				+ "AND attdate = ?";
		try {
			con = DB.con();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, show.getAttdate());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new PJ_showAllTime(
							rs.getString("ename"),
							rs.getString("startwork"),
							rs.getString("endwork")
						));
			}
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return list;
	}
	// 출/퇴근시간 열람 스캐너 메서드
	public void showAllTime() {
		project_main dao = new project_main();
		System.out.println("[출/퇴근시간 열람]");
		System.out.println("열람할 날짜를 입력하세요. ex)2022/10/10");
		String showdate = sc.nextLine();
		System.out.println("사원이름\t출근시간\t퇴근시간");
		for(PJ_showAllTime s : dao.showAllTime(new PJ_showAllTime(showdate))) {
			System.out.print(s.getEname()+"\t");
			System.out.print(s.getStartwork()+"\t");
			System.out.print(s.getEndwork()+"\n");
		}
	}
	
	
	/*
	 
	  연차 Part
	  
	*/
	
	
	// 개인 현재 남은 연차 확인
	public PJ_showAnnual showAnnual(PJ_showAnnual show) {
		PJ_showAnnual sa = null;
		String sql = "SELECT ename, annual\r\n"
				+ "FROM PJEMP\r\n"
				+ "WHERE empno = ?";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, show.getEmpno());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				sa = new PJ_showAnnual(
						rs.getString("ename"),
						rs.getDouble("annual")
						);
				System.out.println(sa.getEname()+"님의 사용가능한 연차는 "+sa.getAnnual()+"일 입니다.");
			}
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return sa;
	}
	
	// 개인 남은 연차 확인 Scanner 메서드
	public void showAnnual() {
		project_main dao = new project_main();
		System.out.println("[남은 연차 확인]");
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		dao.showAnnual(new PJ_showAnnual(empno));
	}
	
	
	
	// 연차 신청
	public void applyAnnual(PJAnnual apply) {
		String sql = "INSERT INTO annualLeave(alno, code, days, startday, endday, contents, empno) \r\n"
		+ "values('ANN'||annual_seq.nextval, ?, ?, to_char(to_date(?, 'YYYY/MM/DD'), 'YYYY/MM/DD'), \r\n"
		+ "to_char(to_date(?, 'YYYY/MM/DD'), 'YYYY/MM/DD'), ?, ?)";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, apply.getCode());
			pstmt.setDouble(2, apply.getDays());
			pstmt.setString(3, apply.getStartday());
			pstmt.setString(4, apply.getEndday());
			pstmt.setString(5, apply.getContents());
			pstmt.setString(6, apply.getEmpno());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("연차신청이 완료되었습니다.");
		}  catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	
	// 연차 신청 Scanner 메서드
	public void applyAnnual() {
		project_main dao = new project_main();
		System.out.println("[연차신청]");
		System.out.println("사용할 연차를 고르세요. 1.연차 2.공가 3.병가 4.반차");
		int code = Integer.parseInt(sc.nextLine());
		double days;
		if(code == 1) {
			System.out.println("사용할 연차 일수를 입력하세요.");
			days = Integer.parseInt(sc.nextLine());
		}else if(code == 4) {
			days = 0.5;
		}else {
			days = 0;
		}
		System.out.println("연차 시작일을 입력하세요. ex.2022/01/01");
		String start = sc.nextLine();
		System.out.println("연차 종료일을 입력하세요. ex.2022/01/02");
		String end = sc.nextLine();
		System.out.println("연차사유를 입력하세요.");
		String contents = sc.nextLine();
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		dao.applyAnnual(new PJAnnual(code, days, start, end, contents, empno));
	}
	
	// 연차 신청서 열람
	public String showAnnaulContents(String alno, String empno) {
		String sql = "SELECT startday, endday, contents\r\n"
				+ "FROM annualLeave\r\n"
				+ "WHERE alno = ?\r\n"
				+ "AND empno =?";
		
		String str = "";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, alno);
			pstmt.setString(2, empno);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				str += "휴가 기간 : "+rs.getString(1)+" ~ "+rs.getString(2)+"\n";
				str += "휴가 사유 : "+rs.getString(3);
			}
		}  catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return str;
	}
	
	// 연차 신청 승인/거절
	public void approvalAnnual(PJAnnual app) {
		String sql = "UPDATE annualLeave\r\n"
				+ "SET approval = ?\r\n"
				+ "WHERE alno = ?\r\n"
				+ "AND empno = ?";
		
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, app.getApproval());
			pstmt.setString(2, app.getAlno());
			pstmt.setString(3, app.getEmpno());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("신청 결과가 저장되었습니다.");
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 연차 계산 메서드
	public void calcu(PJAnnual cal) {
		String sql = "UPDATE PJEMP\r\n"
				+ "SET annual = annual-(SELECT days\r\n"
				+ "				FROM PJEMP e, ANNUALLEAVE a \r\n"
				+ "				WHERE e.empno = a.empno \r\n"
				+ "				AND alno = ?)\r\n"
				+ "WHERE empno = ?";
	
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cal.getAlno());
			pstmt.setString(2, cal.getEmpno());
			pstmt.executeUpdate();
			
			con.commit();
			
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	
	// 연차 사유 열람 및 신청 승인/거절 Scanner 메서드
	// 승인이면 바로 연차에서 마이너스
	public void approvalAnnual() {
		project_main dao = new project_main();
		System.out.println("[연차 신청 승인/거절]");
		System.out.println("결과를 입력할 연차번호를 입력하세요.");
		String alno = sc.nextLine();
		System.out.println("연차를 신청한 사원번호를 입력하세요.");
		String empno = sc.nextLine();
		String contents = dao.showAnnaulContents(alno, empno);
		System.out.println("\n[사원번호 "+empno+"님의 연차신청서]");
		System.out.println(contents);
		System.out.println("\n연차 신청 결과를 입력하세요.[승인/거절]");
		String result = sc.nextLine();
		dao.approvalAnnual(new PJAnnual(result, alno, empno));
		if(result.equals("승인")) {
			dao.calcu(new PJAnnual(alno,empno));
		}
	}
	
	
	// 전 직원 남은 연차 확인
	public List<PJ_showAnnual> showAllAnnual(PJ_showAnnual show){
		List<PJ_showAnnual> alist = new ArrayList<PJ_showAnnual>();
		String sql = "SELECT empno, ename, annual, deptno\r\n"
				+ "FROM PJEMP";
		
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				alist.add(new PJ_showAnnual(
							rs.getString("empno"),
							rs.getString("ename"),
							rs.getDouble("annual"),
							rs.getString("deptno")
						));
			}
		}  catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return alist;
	}
	
	// 전 직원 남은 연차 확인 Scanner 메서드
	public void showAllAnnual() {
		project_main dao = new project_main();
		System.out.println("[전 직원 남은 연차 확인]");
		System.out.println("사원번호\t사원이름\t남은연차\t부서번호");
		for(PJ_showAnnual sa : dao.showAllAnnual(new PJ_showAnnual())) {
			System.out.print(sa.getEmpno()+"\t");
			System.out.print(sa.getEname()+"\t");
			System.out.print(sa.getAnnual()+"\t");
			System.out.print(sa.getDeptno()+"\n");
		}
	}
	
	/*

	  ### 급여관리 Part ###

	*/
	/*
	 
	  급여지급 Part
	  
	*/
	//급여 지급 메서드 - 관리자
	public void giveSal(PJSalary give) {
		String sql = "INSERT INTO salary \r\n"
				+ "VALUES(?, ?, ?, round((SELECT DISTINCT annualSal\r\n"
				+ "							FROM annualsal\r\n"
				+ "							WHERE years = ? )/12, -3))";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, give.getEmpno());
			pstmt.setString(2, give.getYears1());
			pstmt.setString(3, give.getMonths());
			pstmt.setString(4, give.getYears2());
			pstmt.executeUpdate();
			
			con.commit();
		}catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	//급여 지급 Scanner 메서드
	public void giveSal() {
		project_main dao = new project_main();
		System.out.println("[급여 지급]");
		System.out.println("급여를 지급할 사원번호를 입력하세요.");
		String empno = sc.nextLine();
		System.out.println("급여를 지급할 해당 월을 입력하세요.");
		String months = sc.nextLine();
		System.out.println("급여를 지급할 연도를 입력하세요.");
		String years = sc.nextLine();
		dao.giveSal(new PJSalary(empno, years, months, years));
		System.out.println("사원번호 "+empno+"님에게 " +months+"월 급여를 지급하였습니다.");
	}
	
	
	
	//상여금 지급
	public void giveBonus(String empno, String years) {
		String sql = "SELECT ename, bonus\r\n"
				+ "FROM annualSal a, PJEMP e\r\n"
				+ "WHERE a.empno = e.EMPNO \r\n"
				+ "AND a.empno = ?\r\n"
				+ "AND years = ?";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, empno);
			pstmt.setString(2, years);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("ename")+"님에게 상여금 "+rs.getDouble("bonus")+"만원을 지급하였습니다.");
			}
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
	}
	// 상여금 지급 Scanner 메서드
	public void giveBonus() {
		project_main dao = new project_main();
		System.out.println("[상여금 지급]");
		System.out.println("상여금을 지급할 사원번호를 입력하세요.");
		String empno = sc.nextLine();
		System.out.println("해당연도를 입력하세요.");
		String years = sc.nextLine();
		dao.giveBonus(empno, years);
	}
	
	
	/*
	 
	  연봉인상 Part
	  
	*/
	// 연봉 인상 - 관리자
	public void raiseSal(PJSalary02 raise) {
		String sql = "INSERT INTO annualSal \r\n"
				+ "VALUES(?, ?+1, round((SELECT annualSal\r\n"
					+ "					FROM annualSal\r\n"
					+ "					WHERE empno = ?\r\n"
					+ "					AND years = ?)*(1+1*0.01*?), 0), round((SELECT annualSal\r\n"
					+ "																FROM annualSal\r\n"
					+ "																WHERE empno = ?\r\n"
					+ "																AND years = ?)*(1+1*0.01*?), 0)/12*3)";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, raise.getEmpno1());
			pstmt.setString(2, raise.getYears1());
			pstmt.setString(3, raise.getEmpno2());
			pstmt.setString(4, raise.getYears2());
			pstmt.setDouble(5, raise.getHigh1());
			pstmt.setString(6, raise.getEmpno3());
			pstmt.setString(7, raise.getYears3());
			pstmt.setDouble(8, raise.getHigh2());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("연봉인상이 완료되었습니다.");
		}catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 연봉인상 Scanner 메서드
	public void raiseSal() {
		project_main dao = new project_main();
		System.out.println("[연봉인상]");
		System.out.println("인상할 사원의 사원번호를 입력하세요.");
		String empno = sc.nextLine();
		System.out.println("인상률을 입력하세요.");
		double high = Double.parseDouble(sc.nextLine());
		System.out.println("올해 연도를 입력하세요.");
		String years = sc.nextLine();
		dao.raiseSal(new PJSalary02(empno, years, empno, years, high, empno, years, high));
	}
	
	
	/*
	 
	  급여조회 Part
	  
	*/
	// 개일 월별 급여조회
	public List<PJSalary> showMonthSal(PJSalary show){
		List<PJSalary> slist = new ArrayList<PJSalary>();
		String sql = "SELECT e.empno, ename, years, months, sal\r\n"
				+ "FROM salary s , PJEMP e\r\n"
				+ "WHERE s.empno = e.EMPNO \r\n"
				+ "AND s.empno = ?\r\n"
				+ "AND years LIKE '%'||?||'%'";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, show.getEmpno());
			pstmt.setString(2, show.getYears1());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				slist.add(new PJSalary(
							rs.getString("empno"),
							rs.getString("ename"),
							rs.getString("years"),
							rs.getString("months"),
							rs.getDouble("sal")
						));
			}
		}  catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return slist;
	}
	// 개인 월별 급여조회 Scanner 메서드
	public void showMonthSal() {
		project_main dao = new project_main();
		System.out.println("[월별 급여조회]");
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		System.out.println("조회할 연도를 입력하세요. [전체 연도를 확인하려면 enter를 누르세요]");
		String years = sc.nextLine();
		System.out.println("사원번호\t사원이름\t연도\t월\t월급");
		for(PJSalary s : dao.showMonthSal(new PJSalary(empno, years))) {
			System.out.print(s.getEmpno()+"\t");
			System.out.print(s.getEname()+"\t");
			System.out.print(s.getYears1()+"\t");
			System.out.print(s.getMonths()+"\t");
			System.out.print(s.getSal()+"\n");
		}
	}
	
	
	// 개인 연봉 조회 - 사원
	public List<PJSalary> showAnnualSal(PJSalary show){
		List<PJSalary> slist = new ArrayList<PJSalary>();
		String sql = "SELECT e.empno, ename, years, annualSal\r\n"
				+ "FROM annualSal a, PJEMP e\r\n"
				+ "WHERE a.empno = e.EMPNO \r\n"
				+ "AND a.empno = ?\r\n"
				+ "AND years LIKE '%'||?||'%'";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, show.getEmpno());
			pstmt.setString(2, show.getYears1());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				slist.add(new PJSalary(
							rs.getString("empno"),
							rs.getString("ename"),
							rs.getString("years"),
							rs.getDouble("annualSal")
						));
			}
		}  catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return slist;
	}
	// 개인 연봉 조회 Scanner 메서드
	public void showAnnualSal() {
		project_main dao = new project_main();
		System.out.println("[연도별 연봉조회]");
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		System.out.println("조회할 연도를 입력하세요. [전체 연도를 확인하려면 enter를 누르세요]");
		String years = sc.nextLine();
		System.out.println("사원번호\t사원이름\t연도\t연봉");
		for(PJSalary s : dao.showAnnualSal(new PJSalary(empno, years))) {
			System.out.print(s.getEmpno()+"\t");
			System.out.print(s.getEname()+"\t");
			System.out.print(s.getYears1()+"\t");
			System.out.print(s.getAnnualSal()+"\n");
		}
	}
	
	
	// 사원 연봉 조회 - 관리자
	public List<PJSalary> showAllAnnualSal(PJSalary show) {
		List<PJSalary> slist = new ArrayList<PJSalary>();
		String sql = "SELECT e.empno, ename, years, annualSal\r\n"
				+ "FROM annualSal a, PJEMP e\r\n"
				+ "WHERE a.empno = e.EMPNO \r\n"
				+ "AND years LIKE '%'||?||'%'";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, show.getYears1());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				slist.add(new PJSalary(
							rs.getString("empno"),
							rs.getString("ename"),
							rs.getString("years"),
							rs.getDouble("annualSal")
						));
			}
		}  catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return slist;
	}
	// 사원 연봉 조회 Scanner 메서드
	public void showAllAnnualSal() {
		project_main dao = new project_main();
		System.out.println("[전체 사원 연봉조회]");
		System.out.println("조회할 연도를 입력하세요. [전체 연도를 확인하려면 enter를 누르세요]");
		String years = sc.nextLine();
		System.out.println("사원번호\t사원이름\t연도\t연봉");
		for(PJSalary s: dao.showAllAnnualSal(new PJSalary(years))) {
			System.out.print(s.getEmpno()+"\t");
			System.out.print(s.getEname()+"\t");
			System.out.print(s.getYears1()+"\t");
			System.out.print(s.getAnnualSal()+"\n");
		}
			
	}
	
	
	/*

	  ### 계약서 및 증명서발급 Part ###

	*/
	/*
	 
	  근로계약서 Part
	  
	*/
	// 근로계약서 저장 - 사원
	public void writeCt(PJContract write) {
		String sql = "INSERT INTO contract(cno, contents, empno, deptno) "
				+ "VALUES('C'||cno_seq.nextval, ?, ?, ?)";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, write.getContents());
			pstmt.setString(2, write.getEmpno());
			pstmt.setString(3, write.getDeptno());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("근로계약서 저장이 완료되었습니다.");
		}  catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 근로계약서 저장 Scanner 메서드
	public void writeCt() {
		project_main dao = new project_main();
		String path = "C:\\a01_javaexp\\workspace\\javaexp\\src\\project_2\\File\\contract.txt";
		
		System.out.println("[근로계약서 작성]");
		System.out.println("이름을 입력하세요.");
		String name = sc.nextLine();
		System.out.println("근로계약 시작일을 입력하세요. ex.2022/01/01");
		String startday = sc.nextLine();
		System.out.println("직무를 입력하세요.");
		String job = sc.nextLine();
		// 근로계약서 양식안에 이름,시작일,직무를 저장
		String contents = "";
		try {
			contents = Files.readString(Paths.get(path));
			contents = contents.replace("@이름", name);
			contents = contents.replace("@시작일", startday);
			contents = contents.replace("@직무", job);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		System.out.println("부서번호를 입력하세요.");
		String deptno = sc.nextLine();
		dao.writeCt(new PJContract(contents, empno, deptno));
	}
	
	
	// 급여기준 정보설정 & 승인 - 관리자
	public void setCalcu(PJContract set) {
		String sql = "UPDATE contract\r\n"
				+ "SET payday = ?,\r\n"
				+ "	calcu = ?,\r\n"
				+ "	approval = ?\r\n"
				+ "WHERE empno = ?\r\n"
				+ "AND deptno = ?";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, set.getPayday());
			pstmt.setString(2, set.getCalcu());
			pstmt.setString(3, set.getApproval());
			pstmt.setString(4, set.getEmpno());
			pstmt.setString(5, set.getDeptno());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("급여기준 설정이 완료되었습니다.");
		}  catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 급여기준 정보설정 & 승인 Scanner 메서드
	public void setCalcu() {
		project_main dao = new project_main();
		System.out.println("[급여기준 정보설정]");
		System.out.println("급여지급일을 입력하세요.");
		String payday = sc.nextLine();
		System.out.println("단위계산 기준을 입력하세요.");
		String calcu = sc.nextLine();
		System.out.println("계약서 승인여부를 입력하세요. [승인/거부]");
		String approval = sc.nextLine();
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		System.out.println("부서번호를 입력하세요.");
		String deptno = sc.nextLine();
		dao.setCalcu(new PJContract(payday, calcu, approval, empno, deptno));
	}
	
	// 근로계약서 조회
	public List<PJContract> showContract(PJContract show){
		List<PJContract> clist = new ArrayList<PJContract>();
		String sql = "SELECT contents\r\n"
				+ "FROM contract\r\n"
				+ "WHERE empno = ?\r\n"
				+ "OR deptno = ?";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, show.getEmpno());
			pstmt.setString(2, show.getDeptno());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				clist.add(new PJContract(
							rs.getString("contents")
						));
			}
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return clist;
	}
	// 근로계약서 조회 Scanner 메서드
	public void showContract() {
		project_main dao = new project_main();
		System.out.println("[근로계약서 조회]");
		System.out.println("조회할 메뉴를 고르시오. 1)사원별 2)부서별");
		switch(Integer.parseInt(sc.nextLine())) {
			case 1 :
				System.out.println("조회할 사원번호를 입력하세요.");
				String empno = sc.nextLine();
				for(PJContract c : dao.showContract(new PJContract(empno, ""))) {
					System.out.println("[사원번호 "+empno+"님의 근로계약서]");
					System.out.println(c.getContents());
				}
				break;
			case 2 : 
				System.out.println("조회할 부서번호를 입력하세요.");
				String deptno = sc.nextLine();
				for(PJContract c : dao.showContract(new PJContract("", deptno))) {
					System.out.println("["+deptno+"부서의 근로계약서]");
					System.out.println(c.getContents()+"\n\n");
				}
				break;
			default :
				System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
		}
		
	}
	
	
	/*
	 
	  증명서 Part
	  
	*/
	// 증명서 발급신청 - 사원
	public void applyCerti(PJCertificate apply) {
		String sql = "INSERT INTO certificate(cerno, kind, contents, appdate, approval, empno)\r\n"
				+ "VALUES('CER'||cno_seq.nextval, ?, ?, to_char(sysdate,'YYYY/MM/DD'), '승인', ?)";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, apply.getKind());
			pstmt.setString(2, apply.getContents());
			pstmt.setString(3, apply.getEmpno());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("증명서 발급 신청이 완료되었습니다.");
		}  catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 증명서 발급 신청 Scanner 메서드
	public void applyCerti() {
		project_main dao = new project_main();
		System.out.println("[증명서 발급 신청]");
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		System.out.println("신청할 증명서를 선택하세요. 1)재직증명서 2)경력증명서");
		String kind = "";
		String contents = "";
		switch(Integer.parseInt(sc.nextLine())) {
		case 1 :
			kind = "재직증명서";
			String sql = "SELECT ename, registnumber, address, dname, job, to_char(indate, 'YYYY/MM/DD')\r\n"
					+ "FROM PJEMP e , PJDEPT d\r\n"
					+ "WHERE e.deptno = d.deptno\r\n"
					+ "AND empno = ?";
			String path = "C:\\a01_javaexp\\workspace\\javaexp\\src\\project_2\\File\\workCertificate.txt";
			try {
				con = DB.con();
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, empno);
				
				rs = pstmt.executeQuery();
				
				contents = Files.readString(Paths.get(path));
				while(rs.next()) {
					contents = contents.replace("@이름", rs.getString(1));
					contents = contents.replace("@주민번호", rs.getString(2));
					contents = contents.replace("@현주소", rs.getString(3));
					contents = contents.replace("@부서", rs.getString(4));
					contents = contents.replace("@직급", rs.getString(5));
					contents = contents.replace("@입사일", rs.getString(6));
				}
			} catch (SQLException e) {
				System.out.println("DB 예외:"+e.getMessage());
			} catch (Exception e) {
				System.out.println("일반 예외:"+e.getMessage());
			} finally {
				DB.close(rs, pstmt, con);
			}
			break;
		case 2 : 
			kind = "경력증명서";
			String sql1 = "SELECT ename, registnumber, TO_char(indate,'YYYY/MM/DD'), dname, job, address, nvl(TO_char(outdate,'YYYY/MM/DD'), ' ')\r\n"
					+ "FROM PJEMP e , PJDEPT d\r\n"
					+ "WHERE e.deptno = d.deptno\r\n"
					+ "AND empno = ?";
			String path1 = "C:\\a01_javaexp\\workspace\\javaexp\\src\\project_2\\File\\careerCertificate.txt";
			try {
				con = DB.con();
				
				pstmt = con.prepareStatement(sql1);
				pstmt.setString(1, empno);
				
				rs = pstmt.executeQuery();
				
				contents = Files.readString(Paths.get(path1));
				while(rs.next()) {
					contents = contents.replace("@이름", rs.getString(1));
					contents = contents.replace("@주민번호", rs.getString(2));
					contents = contents.replaceAll("@입사일", rs.getString(3));
					contents = contents.replace("@부서", rs.getString(4));
					contents = contents.replace("@직급", rs.getString(5));
					contents = contents.replace("@현주소", rs.getString(6));
					contents = contents.replace("@퇴사일", rs.getString(7));
				}
			} catch (SQLException e) {
				System.out.println("DB 예외:"+e.getMessage());
			} catch (Exception e) {
				System.out.println("일반 예외:"+e.getMessage());
			} finally {
				DB.close(rs, pstmt, con);
			}
			break;
		default :
			System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
		}
		
		dao.applyCerti(new PJCertificate(kind, contents, empno));
	}
	
	// 신청한 증명서 조회 - 사원&관리자
	public List<PJCertificate> showCerti(String kind, String appdate, String empno){
		List<PJCertificate> clist = new ArrayList<PJCertificate>();
		String sql = "SELECT contents\r\n"
				+ "FROM certificate\r\n"
				+ "WHERE kind = ?\r\n"
				+ "AND appdate = to_char(to_date(?,'YYYY/MM/DD'),'YYYY/MM/DD')\r\n"
				+ "AND empno = ?";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, appdate);
			pstmt.setString(3, empno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				clist.add(new PJCertificate(rs.getString(1)));
			}
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return clist;
	}
	// 신청한 증명서 조회 Scanner 메서드
	public void showCerti() {
		project_main dao = new project_main();
		System.out.println("[증명서 조회]");
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		System.out.println("증명서 종류를 입력하세요. 1.재직증명서 2.경력증명서");
		int num = Integer.parseInt(sc.nextLine());
		String kind = "";
		if(num == 1) {
			kind = "재직증명서";
		}else if(num == 2) {
			kind = "경력증명서";
		}else {
			System.out.println("잘못 입력하셨습니다. 다시 시도해주세요.");
		}
		System.out.println("신청날짜를 입력하세요.");
		String appdate = sc.nextLine();
		System.out.println("사원번호 "+empno+"님의 증명서");
		for(PJCertificate c : dao.showCerti(kind, appdate, empno)) {
			System.out.println(c.getContents());
		}
	}
	
	
	
	/*

	  ### 전자 결재 Part ###

	*/
	// 기안서 작성
	public void writeDraft(PJDraft write) {
		String sql = "INSERT INTO draft(dno, dDate, discuss, dname, empno, ename, title, contents)\r\n"
				+ "VALUES('DRA'||dno_seq.nextval, to_char(sysdate,'YYYY/MM/DD'), \r\n"
				+ "(SELECT dname FROM PJDEPT WHERE deptno = ?), \r\n"
				+ "(SELECT dname FROM PJDEPT WHERE deptno = ?), ?,\r\n"
				+ "(SELECT ename FROM PJEMP WHERE empno = ?), ?, ?)";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, write.getDisno());
			pstmt.setString(2, write.getDeptno());
			pstmt.setString(3, write.getEmpno());
			pstmt.setString(4, write.getWhereempno());
			pstmt.setString(5, write.getTitle());
			pstmt.setString(6, write.getContents());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("기안서 작성이 완료되었습니다.");
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	
	public void updateDraft(String draft, String dno) {
		String sql = "UPDATE draft\r\n"
				+ "SET draft = ?\r\n"
				+ "WHERE dno = ?";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, draft);
			pstmt.setString(2, dno);
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("기안서 저장이 완료되었습니다.");
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 기안서 작성&저장 Scanner 메서드
	public void writeDraft() {
		project_main dao = new project_main();
		System.out.println("[기안서 작성]");
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		System.out.println("협의할 부서번호를 입력하세요.");
		String disno = sc.nextLine();
		System.out.println("본인의 부서번호을 입력하세요.");
		String deptno = sc.nextLine();
		System.out.println("기안서 제목을 입력하세요.");
		String title = sc.nextLine();
		System.out.println("내용을 입력하세요.");
		String contents = sc.nextLine();
		dao.writeDraft(new PJDraft(disno, deptno, empno, empno, title, contents));
		
		System.out.println("[기안서 저장]");
		System.out.println("문서번호를 입력하세요.");
		String dno = sc.nextLine();
		// 기안서 저장
		String sql = "SELECT dno, dDate, discuss, dname, ename, title, contents\r\n"
				+ "FROM draft\r\n"
				+ "WHERE dno = ?";
		String path = "C:\\a01_javaexp\\workspace\\javaexp\\src\\project_2\\File\\draft.txt";
		String content = "";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dno);
			
			rs = pstmt.executeQuery();
			
			content = Files.readString(Paths.get(path));
			while(rs.next()) {
				content = content.replace("@문서번호", rs.getString(1));
				content = content.replace("@기안일자", rs.getString(2));
				content = content.replace("@협의부서", rs.getString(3));
				content = content.replace("@기안부서", rs.getString(4));
				content = content.replace("@이름", rs.getString(5));
				content = content.replace("@제목", rs.getString(6));
				content = content.replace("@내용", rs.getString(7));
			}
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		dao.updateDraft(content, dno);
	}
	
	
	// 결재선 설정 - 관리자
	public void setApproval(PJApproval set) {
		String sql = "INSERT INTO approval(dno, approval1, approval2, approval3) \r\n"
				+ "VALUES(?, (SELECT ename FROM PJEMP WHERE empno = ? AND job = ?), \r\n"
				+ "	   (SELECT ename FROM PJEMP WHERE empno = ? AND job = ?), \r\n"
				+ "	   (SELECT ename FROM PJEMP WHERE empno = ? AND job = ?))";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, set.getDno());
			pstmt.setString(2, set.getEmpno1());
			pstmt.setString(3, set.getJob1());
			pstmt.setString(4, set.getEmpno2());
			pstmt.setString(5, set.getJob2());
			pstmt.setString(6, set.getEmpno3());
			pstmt.setString(7, set.getJob3());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("문서번호 "+set.getDno()+"의 결재선 설정이 완료되었습니다.");
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 결제선 설정 Scanner 메서드
	public void setApproval() {
		project_main dao = new project_main();
		System.out.println("[결재선 설정]");
		System.out.println("결재선을 설정할 문서번호를 입력하세요.");
		String dno = sc.nextLine();
		System.out.println("결재자1의 사원번호를 입력하세요.");
		String app1 = sc.nextLine();
		System.out.println("결재자1의 직책을 입력하세요.");
		String job1 = sc.nextLine();
		System.out.println("결재자2의 사원번호를 입력하세요.");
		String app2 = sc.nextLine();
		System.out.println("결재자2의 직책을 입력하세요.");
		String job2 = sc.nextLine();
		System.out.println("결재자3의 사원번호를 입력하세요.");
		String app3 = sc.nextLine();
		System.out.println("결재자3의 직책을 입력하세요.");
		String job3 = sc.nextLine();
		dao.setApproval(new PJApproval(dno, app1, job1, app2, job2, app3, job3));
		
	}
	
	
	// 기안 조회 - 사원
	public List<PJDraft> showDraft(PJDraft show){
		List<PJDraft> dlist = new ArrayList<PJDraft>();
		String sql = "SELECT draft\r\n"
				+ "FROM draft\r\n"
				+ "WHERE empno = ?\r\n"
				+ "AND title LIKE '%'||?||'%'";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, show.getEmpno());
			pstmt.setString(2, show.getTitle());
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dlist.add(new PJDraft(
							rs.getString("draft")
						));
			}
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return dlist;
	}
	// 기안 조회 Scanner 메서드
	public void showDraft() {
		project_main dao = new project_main();
		System.out.println("[기안 조회]");
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		System.out.println("조회할 제목을 입력하세요.");
		String title = sc.nextLine();
		for(PJDraft d : dao.showDraft(new PJDraft(empno, title))){
			System.out.println(d.getDraft());
		}
	}
	
	// 기안서 수정
	public void modifyDraft(PJDraft mod) {
		String sql = "UPDATE draft\r\n"
				+ "SET title = ?,\r\n"
				+ "	contents = ?\r\n"
				+ "WHERE dno = ?\r\n"
				+ "AND empno = ?";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mod.getTitle());
			pstmt.setString(2, mod.getContents());
			pstmt.setString(3, mod.getDno());
			pstmt.setString(4, mod.getEmpno());
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("기안서 수정이 완료되었습니다.");
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 기안서 수정 Scanner 메서드
	public void modifyDraft() {
		project_main dao = new project_main();
		System.out.println("[기안서 수정]");
		System.out.println("수정할 문서번호를 입력하세요.");
		String dno = sc.nextLine();
		System.out.println("수정할 기안서 제목을 입력하세요.");
		String title = sc.nextLine();
		System.out.println("수정할 내용을 입력하세요.");
		String contents = sc.nextLine();
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		dao.modifyDraft(new PJDraft(title, contents, dno, empno));
		// 수정된 기안서 저장
		String sql = "SELECT dno, dDate, discuss, dname, ename, title, contents\r\n"
				+ "FROM draft\r\n"
				+ "WHERE dno = ?";
		String path = "C:\\a01_javaexp\\workspace\\javaexp\\src\\project_2\\File\\draft.txt";
		String content = "";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dno);
			
			rs = pstmt.executeQuery();
			
			content = Files.readString(Paths.get(path));
			while(rs.next()) {
				content = content.replace("@문서번호", rs.getString(1));
				content = content.replace("@기안일자", rs.getString(2));
				content = content.replace("@협의부서", rs.getString(3));
				content = content.replace("@기안부서", rs.getString(4));
				content = content.replace("@이름", rs.getString(5));
				content = content.replace("@제목", rs.getString(6));
				content = content.replace("@내용", rs.getString(7));
			}
		} catch (SQLException e) {
			System.out.println("DB 예외:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		dao.updateDraft(content, dno);
	}
	
	// 기안서 삭제 - 사원
	public void deleteDraft(String dno, String empno) {
		String sql = "DELETE draft\r\n"
				+ "WHERE dno = ?"
				+ "AND empno = ?";
		try {
			con = DB.con();
			
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dno);
			pstmt.setString(2, empno);
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("삭제가 완료되었습니다.");
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback에러:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("기타 에러:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 결재선 삭제 
	public void deleteApproval(String dno) {
		String sql = "DELETE approval\r\n"
				+ "WHERE dno = ?";
		try {
			con = DB.con();
			
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dno);;
			pstmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback에러:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("기타 에러:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 기안서&결재선 삭제 Scanner 메서드
	public void deleteDraft() {
		project_main dao = new project_main();
		System.out.println("[기안서 삭제]");
		System.out.println("삭제할 문서번호를 입력하세요.");
		String dno = sc.nextLine();
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		dao.deleteApproval(dno);
		dao.deleteDraft(dno, empno);
	}
	
	
	
	// 결재선1
	public void state1Approval(String state, String empno, String job, String dno) {
		String sql = "UPDATE approval\r\n"
				+ "SET state1 = ?\r\n"
				+ "WHERE approval1 = (SELECT ename FROM PJEMP WHERE empno = ? AND job = ?)"
				+ "AND dno = ?";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, state);
			pstmt.setString(2, empno);
			pstmt.setString(3, job);
			pstmt.setString(4, dno);
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("결재가 완료되었습니다.");
			
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 결재선2
	public void state2Approval(String state, String empno, String job, String dno) {
		String sql = "UPDATE approval\r\n"
				+ "SET state2 = ?\r\n"
				+ "WHERE state1 = '승인'\r\n"
				+ "AND approval2 = (SELECT ename FROM PJEMP WHERE empno = ? AND job = ?)"
				+ "AND dno = ?";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, state);
			pstmt.setString(2, empno);
			pstmt.setString(3, job);
			pstmt.setString(4, dno);
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("결재가 완료되었습니다.");
			
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 결재선3
	public void state3Approval(String state, String empno, String job, String dno) {
		String sql = "UPDATE approval\r\n"
				+ "SET state2 = ?\r\n"
				+ "WHERE state1 = '승인'\r\n"
				+ "AND state2 = '승인'\r\n"
				+ "AND approval3 = (SELECT ename FROM PJEMP WHERE empno = ? AND job = ?)"
				+ "AND dno = ?";
		try {
			con = DB.con();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, state);
			pstmt.setString(2, empno);
			pstmt.setString(3, job);
			pstmt.setString(4, dno);
			pstmt.executeUpdate();
			
			con.commit();
			System.out.println("결재가 완료되었습니다.");
			
		} catch (SQLException e) {
			System.out.println("DB 관련 예외:"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 예외:"+e1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(pstmt, con);
		}
	}
	// 결재 Scannr 메서드
	public void stateApproval() {
		project_main dao = new project_main();
		System.out.println("[결재]");
		System.out.println("결재선 번호를 입력하세요. 1.팀장 2.대리 3.과장");
		int num = Integer.parseInt(sc.nextLine());
		System.out.println("사원번호를 입력하세요.");
		String empno = sc.nextLine();
		System.out.println("직책을 입력하세요.");
		String job = sc.nextLine();
		System.out.println("승인/취소 여부를 입력하세요.");
		String state = sc.nextLine();
		System.out.println("결재할 문서번호를 입력하세요.");
		String dno = sc.nextLine();
		switch(num) {
			case 1:
				dao.state1Approval(state, empno, job, dno);
				break;
			case 2:
				dao.state2Approval(state, empno, job, dno);
				break;
			case 3:
				dao.state3Approval(state, empno, job, dno);
				break;
			default :
				System.out.println("잘못된 번호입니다.");
		}
	}
	
	// * MAIN *
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		project_main dao = new project_main();
		Scanner sc = new Scanner(System.in);
		// 부서정보 입력
//		dao.insertDeptInfo(new PJDept("D10", "인사과"));
//		dao.insertDeptInfo(new PJDept("D20", "기획과"));
//		dao.insertDeptInfo(new PJDept("D30", "회계과"));
//		dao.insertDeptInfo(new PJDept("D40", "총무과"));
//		dao.insertDeptInfo(new PJDept("D50", "영업과"));
		
		
		System.out.println("[로그인]");
		System.out.println("1.관리자 2.사원");
		switch(Integer.parseInt(sc.nextLine())) {
			case 1 :
				// [로그인 - 관리자]
				dao.mgrlogin();
				boolean mgr = true;
				while(mgr) {
					System.out.println("[관리자 Page]");
					System.out.println("=========메뉴 선택=========");
					System.out.println("1.인사관리");
					System.out.println("2.급여관리");
					System.out.println("3.계약서 및 증명서");
					System.out.println("4.근태관리");
					System.out.println("5.전자결재");
					
					switch(Integer.parseInt(sc.nextLine())) {
						case 1 :
							System.out.println("[인사관리 Page]");
							System.out.println("=========메뉴 선택=========");
							System.out.println("1.사원 기초정보입력");
							System.out.println("2.사원 계정생성");
							System.out.println("3.퇴사예정자 데이터&계정 삭제");
							System.out.println("4.비밀번호 조기화");
							System.out.println("5.개인정보변경");
							switch(Integer.parseInt(sc.nextLine())) {
								case 1 :
									// [기초정보입력 - 관리자]
									dao.insertEmpInfo();
									break;
								case 2 :
									// [사원계정 생성 - 관리자]
									dao.makeAccount();
									break;
								case 3 :
									// [퇴사예정자 데이터&계정 삭제 - 관리자]
									dao.deleteAccount();
									break;
								case 4 :
									// [비밀번호 초기화 - 관리자]
									dao.resetPass();
									break;
								case 5 :
									// [개인정보변경 - 관리자]
									dao.changeEmpInfo();
									break;
								default :
									System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
									break;
							}
							break;
						case 2 : 
							System.out.println("[급여관리 Page]");
							System.out.println("=========메뉴 선택=========");
							System.out.println("1.급여 지급");
							System.out.println("2.상여금 지급");
							System.out.println("3.연봉 인상");
							System.out.println("4.전 사원 연봉 조회");
							switch(Integer.parseInt(sc.nextLine())) {
								case 1 :
									// [급여지급 - 관리자]
									dao.giveSal();
									break;
								case 2 :
									// [상여금 지급 - 관리자]
									dao.giveBonus();
									break;
								case 3 :
									// [연봉인상 - 관리자]
									dao.raiseSal();
									break;
								case 4 :
									// [전 사원 연봉 조회 - 관리자]
									dao.showAllAnnualSal();
									break;
								default :
									System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
									break;
							}
							break;
						case 3 :
							System.out.println("[계약서 및 증명서 Page]");
							System.out.println("=========메뉴 선택=========");
							System.out.println("1.급여기준 정보설정 & 승인");
							System.out.println("2.근로계약서 조회");
							System.out.println("3.신청한 증명서 조회");
							switch(Integer.parseInt(sc.nextLine())) {
								case 1 :
									// [급여기준 정보설정 & 승인 - 관리자]
									dao.setCalcu();
									break;
								case 2 :
									// [근로계약서 조회 - 관리자]
									dao.showContract();
									break;
								case 3 :
									// [증명서 조회]
									dao.showCerti();
									break;
								default :
									System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
									break;
							}
							break;
						case 4 :
							System.out.println("[근태관리 Page]");
							System.out.println("=========메뉴 선택=========");
							System.out.println("1.근무제도 설정");
							System.out.println("2.근무제도 현황 열람");
							System.out.println("3.출/퇴근 시간 열람");
							System.out.println("4.연차신청 승인/거절");
							System.out.println("5.전직원 연차 현황");
							switch(Integer.parseInt(sc.nextLine())) {
								case 1 :
									// [근무제도설정 - 관리자]
									dao.setSystem();
									break;
								case 2 :
									// [근무제도 현황 열람 - 관리자]
									dao.showWorkSystem();
									break;
								case 3 :
									// [출/퇴근 시간 열람 - 관리자]
									dao.showAllTime();
									break;
								case 4 :
									// [연차신청 승인/거절 - 관리자]
									dao.approvalAnnual();
									break;
								case 5 :
									// [전직원 연차 현황 - 관리자]
									dao.showAllAnnual();
									break;
								default :
									System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
									break;
							}
							break;
						case 5 :
							System.out.println("[전자결재 Page]");
							System.out.println("=========메뉴 선택=========");
							System.out.println("1.결재선 설정");
							switch(Integer.parseInt(sc.nextLine())) {
								case 1 :
									// [결재선 설정 - 관리자]
									dao.setApproval();
									break;
								default :
									System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
									break;
								}
							break;
						default :
							System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
							break;
					}
					System.out.println("\n* 관리자 Page로 돌아가시려면 1을 입력, 종료하시려면 2을 입력하세요. *");
					if(Integer.parseInt(sc.nextLine()) == 2) {
						mgr = false;
					}
				}
				System.out.println("종료되었습니다.");
				break;
			case 2:
				// [로그인 - 사원]
				dao.emplogin();
				boolean emp = true;
				while(emp) {
					System.out.println("[사원 페이지]");
					System.out.println("=========메뉴 선택=========");
					System.out.println("1.인사관리");
					System.out.println("2.급여관리");
					System.out.println("3.계약서 및 증명서");
					System.out.println("4.근태관리");
					System.out.println("5.전자결재");
					switch(Integer.parseInt(sc.nextLine())) {
						case 1 :
							System.out.println("[인사관리 Page]");
							System.out.println("=========메뉴 선택=========");
							System.out.println("1.부서별 사원정보 열람");
							System.out.println("2.비밀번호 설정");
							switch(Integer.parseInt(sc.nextLine())) {
								case 1 :
									// [부서별 사원 정보 열람 - 직원]
									dao.showList();
									break;
								case 2 :
									// [비밀번호 설정 - 직원]
									dao.changePass();
									break;
								
								default :
									System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
									break;
							}
							break;
						case 2 :
							System.out.println("[급여관리 Page]");
							System.out.println("=========메뉴 선택=========");
							System.out.println("1.개인 월별 급여 조회");
							System.out.println("2.개인 연봉 조회");
							switch(Integer.parseInt(sc.nextLine())) {
								case 1 :
									// [개인 월별 급여 조회 - 사원]
									dao.showMonthSal();
									break;
								case 2 :
									// [개인 연봉 조회 - 사원]
									dao.showAnnualSal();
									break;
								default :
									System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
									break;
							}
							break;
						case 3 :
							System.out.println("[계약서 및 증명서 Page]");
							System.out.println("=========메뉴 선택=========");
							System.out.println("1.근로계약서 작성 및 저장");
							System.out.println("2.증명서 발급신청");
							System.out.println("3.신청한 증명서 조회");
							switch(Integer.parseInt(sc.nextLine())) {
								case 1 :
									// [근로계약서 작성 및 저장 - 사원]
									dao.writeCt();
									break;
								case 2 :
									// [증명서 발급신청 - 사원]
									dao.applyCerti();
									break;
								case 3 :
									// [신청한 증명서 조회]
									dao.showCerti();
									break;
								default :
									System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
									break;
							}
							break;
						case 4 :
							System.out.println("[근태관리 Page]");
							System.out.println("=========메뉴 선택=========");
							System.out.println("1.근무제도 신청");
							System.out.println("2.출근시간저장");
							System.out.println("3.퇴근시간저장");
							System.out.println("4.연차신청");
							System.out.println("5.개인 남은 연차 확인");
							switch(Integer.parseInt(sc.nextLine())) {
								case 1 :
									// [근무제도 신청 - 사원]
									dao.applySystem();
									break;
								case 2 :
									// [출근시간저장 - 사원]
									dao.saveStartTime();
									break;
								case 3 :
									// [퇴근시간저장 - 사원]
									dao.saveEndTime();
									break;
								case 4 :
									// [연차신청 - 사원]
									dao.applyAnnual();
									break;
								case 5 :
									// [개인 남은 연차 확인 - 사원]
									dao.showAnnual();
									break;
								default :
									System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
									break;
							}
							break;
						case 5 :
							System.out.println("[전자결재 Page]");
							System.out.println("=========메뉴 선택=========");
							System.out.println("1.기안서 작성&저장");
							System.out.println("2.기안서 조회");
							System.out.println("3.기안서 수정&저장");
							System.out.println("4.기안서&결재선 삭제");
							System.out.println("5.결재");
							switch(Integer.parseInt(sc.nextLine())) {
								case 1 :
									// [기안서 작성&저장 - 사원]
									dao.writeDraft();
									break;
								case 2 :
									// [기안서 조회 - 사원]
									dao.showDraft();
									break;
								case 3 :
									// [기안서 수정&저장 - 사원]
									dao.modifyDraft();
									break;
								case 4 :
									// [기안서&결재선 삭제 - 사원]
									dao.deleteDraft();
									break;
								case 5 :
									// [결재 - 사원]
									dao.stateApproval();
									break;
								default :
									System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
									break;
								}
							break;
						default :
							System.out.println("잘못된 메뉴를 선택했습니다. 처음부터 다시 시도하세요.");
							break;
					}
					System.out.println("\n* 사원 Page로 돌아가시려면 1을 입력, 종료하시려면 2을 입력하세요. *");
					if(Integer.parseInt(sc.nextLine()) == 2) {
						emp = false;
					}
				}
				System.out.println("종료되었습니다.");
				break;
			default :
				System.out.println("해당 내용은 없습니다.");
				break;
		}
		
	}
}
