//"use client";
import "./companyForm.css";
import { redirect } from "next/navigation";

export default function companySignupFrom() {
  async function createCompany(formData: FormData) {
    "use server";

    const rawFormData = {
      userId: formData.get("userId"),
      password: formData.get("password"),
      passwordCheck: formData.get("passwordCheck"),
      companyname: formData.get("companyname"),
      businesstype: formData.get("businesstype"),
      field: formData.get("field"),
      website: formData.get("website"),
      address: formData.get("address"),
      employeeCount: formData.get("employeeCount"),
      introduction: formData.get("introduction"),
      established: formData.get("established"),
    };

    console.log(rawFormData);

    const response = await fetch("http://localhost:8080/companies", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(rawFormData),
    });
    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(`Failed to fetch company signup: ${errorData.message}`);
    }

    redirect("/");
  }

  return (
      <div className="container signup">
        <header>
          <h3>기업 회원가입</h3>
        </header>
        <div className="formContainer">
          <form action={createCompany} className="form">
            <div className="formGroup">
              <label htmlFor="password">아이디</label>
              <input
                  type="text"
                  name="userId"
                  required
                  placeholder="아이디"
                  className="input"
              />
            </div>

            <div className={"formGroup"}>
              <label htmlFor="password">비밀번호 SecurePass1!</label>
              <input
                  type="password"
                  name="password"
                  required
                  placeholder="비밀번호"
                  className="input"
              />
            </div>

            <div className="formGroup">
              <label htmlFor="passwordCheck">비밀번호 확인</label>
              <input
                  type="password"
                  name="passwordCheck"
                  required
                  placeholder="비밀번호 확인"
                  className="input"
              />
            </div>

            <div className="formGroup">
              <label htmlFor="companyname">기업이름</label>
              <input
                  type="text"
                  name="companyname"
                  required
                  placeholder="기업 이름"
                  className="input"
              />
            </div>

            <div className="formGroup">
              <label htmlFor="businesstype">업종</label>
              <input
                  type="text"
                  name="businesstype"
                  required
                  placeholder="업종"
                  className="input"
              />
            </div>

            <div className="formGroup">
              <label htmlFor="field">모집 분야</label>
              <select name="field" required className="input">
                <option value="">선택하세요</option>
                <option value="Front_End">프론트엔드</option>
                <option value="Back_End">백엔드</option>
                <option value="Full_Stack">풀스택</option>
                <option value="DBManege">DB 관리</option>
                <option value="ServerMange">서버 관리</option>
              </select>
            </div>

            <div className="formGroup">
              <label htmlFor="website">사이트</label>
              <input
                  type="url"
                  name="website"
                  required
                  placeholder="사이트"
                  className="input"
              />
            </div>

            <div className="formGroup">
              <label htmlFor="address">주소</label>
              <input
                  type="text"
                  name="address"
                  required
                  placeholder="주소"
                  className="input"
              />
            </div>

            <div className="formGroup">
              <label htmlFor="employeeCount">사원 수</label>
              <input
                  type="text"
                  name="employeeCount"
                  required
                  placeholder="사원 수"
                  className="input"
              />
            </div>

            <div className="formGroup">
              <label htmlFor="introduction">기업 소개</label>
              <textarea
                  name="introduction"
                  required
                  placeholder="기업 소개"
                  className="textarea"
              ></textarea>
            </div>

            <div className="formGroup">
              <label htmlFor="established">설립연도</label>
              <input
                  type="date"
                  name="established"
                  required
                  placeholder="설립연도"
                  className="input"
              />
            </div>

            <div className="buttonGroup">
              <button type="submit" className="submitButton">
                회원가입
              </button>
              <button type="button" className="cancelButton">
                취소
              </button>
            </div>
          </form>
        </div>
      </div>
  );
}

